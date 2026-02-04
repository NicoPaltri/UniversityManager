package application.applicationutils.openwindowmanager;

import customexceptions.encapsulateexception.EncapsulateIOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.Region;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public final class OpenWindowUtils {
    public <C> void openNewWindow(WindowRequest<C> windowRequest) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    Objects.requireNonNull(getClass().getResource(windowRequest.getWindowPath()),
                            "FXML non trovato: " + windowRequest.getWindowPath())
            );
            Parent root = loader.load();

            if (windowRequest.getControllerInitializer() != null) {
                C controller = loader.getController();
                windowRequest.getControllerInitializer().accept(controller);
            }

            Scene scene = new Scene(root, windowRequest.getWidth(), windowRequest.getHeight());

            addStylesheet(scene, "/styles/generalStyleSheet.css");
            if (windowRequest.getSpecificCssPath() != null) {
                addStylesheet(scene, windowRequest.getSpecificCssPath());
            }

            Stage stage = windowRequest.getStage();
            if (stage == null) {
                stage = new Stage();
            }

            stage.setTitle(windowRequest.getWindowName());
            stage.setScene(scene);

            stage.setResizable(windowRequest.isResizable());

            if (windowRequest.isModal()) {
                stage.initModality(Modality.APPLICATION_MODAL);
            }

            if (windowRequest.getMainPane() != null) {
                stage.initOwner(windowRequest.getMainPane().getScene().getWindow());
            }

            if (windowRequest.getOnClose() != null) {
                stage.setOnHidden(e -> windowRequest.getOnClose().run());
            }

            stage.show();

        } catch (IOException e) {
            throw new EncapsulateIOException(windowRequest.getWindowPath(), e);
        }
    }

    private void addStylesheet(Scene scene, String cssPath) {
        if (cssPath == null || cssPath.isBlank()) return;

        URL cssUrl = Objects.requireNonNull(
                getClass().getResource(cssPath),
                "CSS non trovato: " + cssPath
        );
        scene.getStylesheets().add(cssUrl.toExternalForm());
    }

    public static void errorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Errore di input");
        alert.setHeaderText("Operazione non valida");
        alert.setContentText(message);

        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.showAndWait();

    }

}
