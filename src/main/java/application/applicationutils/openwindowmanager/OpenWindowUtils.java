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
                    Objects.requireNonNull(getClass().getResource(windowRequest.windowPath),
                            "FXML non trovato: " + windowRequest.windowPath)
            );
            Parent root = loader.load();

            if (windowRequest.controllerInitializer != null) {
                @SuppressWarnings("unchecked")
                C controller = (C) loader.getController();
                windowRequest.controllerInitializer.accept(controller);
            }

            Scene scene = new Scene(root, windowRequest.width, windowRequest.height);

            addStylesheet(scene, "/styles/generalStyleSheet.css");
            if (windowRequest.specificCssPath != null && !windowRequest.specificCssPath.isBlank()) {
                addStylesheet(scene, windowRequest.specificCssPath);
            }

            Stage stage = windowRequest.stage;
            if (stage == null) {
                stage = new Stage();
            }

            stage.setTitle(windowRequest.windowName);
            stage.setScene(scene);

            stage.setResizable(windowRequest.resizable);

            if (windowRequest.modal) {
                stage.initModality(Modality.APPLICATION_MODAL);
            }

            if (windowRequest.mainPane != null) {
                stage.initOwner(windowRequest.mainPane.getScene().getWindow());
            }

            if (windowRequest.onClose != null) {
                stage.setOnHidden(e -> windowRequest.onClose.run());
            }

            stage.show();

            return;

        } catch (IOException e) {
            throw new EncapsulateIOException(windowRequest.windowPath, e);
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
