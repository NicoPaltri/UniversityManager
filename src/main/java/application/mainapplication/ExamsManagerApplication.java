package application.mainapplication;

import application.OpenWindowUtils;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ExamsManagerApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        OpenWindowUtils utils=new OpenWindowUtils();
        utils.openNewWindow(
                "University Manager",
                "/stages/MainStage.fxml",
                "/styles/specificMainStage.css",
                null,
                null
        );

    }

}
