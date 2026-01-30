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

    public void openNewWindow(String windowName, String windowPath, String cssPath, Pane mainPane, Runnable onClose) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(windowPath));
            Parent root = loader.load();

            Scene scene = new Scene(root);

            //css
            URL cssUrl = Objects.requireNonNull(
                    getClass().getResource(cssPath),
                    "CSS non trovato: " + cssPath
            );
            scene.getStylesheets().add(cssUrl.toExternalForm());

            Stage stage = new Stage();
            stage.setTitle(windowName);
            stage.setScene(scene);

            //blocco la finestra principale
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(mainPane.getScene().getWindow());

            //Callback: cosa fare quando la finestra viene chiusa
            stage.setOnHidden(e -> {
                if (onClose != null) {
                    onClose.run();
                }
            });

            stage.show();

        } catch (IOException e) {
            throw new EncapsulateIOException(windowPath, e);
        }
    }

    public <C> void openNewWindow(
            String windowName,
            String windowPath,
            String cssPath,
            Pane mainPane,
            java.util.function.Consumer<C> controllerInitializer,
            Runnable onClose
    ) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(windowPath));
            Parent root = loader.load();

            // prende controller creato da FXMLLoader
            C controller = loader.getController();
            if (controllerInitializer != null) {
                controllerInitializer.accept(controller);
            }

            //css
            Scene scene = new Scene(root);

            URL cssUrl = Objects.requireNonNull(
                    getClass().getResource(cssPath),
                    "CSS non trovato: " + cssPath
            );
            scene.getStylesheets().add(cssUrl.toExternalForm());


            Stage stage = new Stage();
            stage.setTitle(windowName);
            stage.setScene(scene);

            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(mainPane.getScene().getWindow());

            stage.setOnHidden(e -> {
                if (onClose != null) onClose.run();
            });

            stage.show();

        } catch (IOException e) {
            throw new EncapsulateIOException(windowPath, e);
        }
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
