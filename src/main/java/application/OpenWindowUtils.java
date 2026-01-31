package application;

import customexceptions.encapsulateexception.EncapsulateIOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class OpenWindowUtils {
    public OpenWindowUtils() {
    }

    public void openNewWindow(
            String windowName,
            String windowPath,
            String overrideCssPath,
            Pane mainPane,
            Runnable onClose
    ) {
        openNewWindowInternal(windowName, windowPath, overrideCssPath, mainPane, null, onClose);
    }


    public <C> void openNewWindow(
            String windowName,
            String windowPath,
            String overrideCssPath,
            Pane mainPane,
            java.util.function.Consumer<C> controllerInitializer,
            Runnable onClose
    ) {
        openNewWindowInternal(windowName, windowPath, overrideCssPath, mainPane, controllerInitializer, onClose);
    }

    private <C> void openNewWindowInternal(
            String windowName,
            String windowPath,
            String overrideCssPath,
            Pane mainPane,
            java.util.function.Consumer<C> controllerInitializer,
            Runnable onClose
    ) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    Objects.requireNonNull(getClass().getResource(windowPath),
                            "FXML non trovato: " + windowPath)
            );
            Parent root = loader.load();

            if (controllerInitializer != null) {
                @SuppressWarnings("unchecked")
                C controller = (C) loader.getController();
                controllerInitializer.accept(controller);
            }

            Scene scene = new Scene(root);

            // 1) CSS generale
            addStylesheet(scene, "/styles/generalStyleSheet.css");
            // 2) CSS di override (viene dopo => vince)
            addStylesheet(scene, overrideCssPath);

            Stage stage = new Stage();
            stage.setTitle(windowName);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(mainPane.getScene().getWindow());

            if (onClose != null) stage.setOnHidden(e -> onClose.run());

            stage.show();

        } catch (IOException e) {
            throw new EncapsulateIOException(windowPath, e);
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
