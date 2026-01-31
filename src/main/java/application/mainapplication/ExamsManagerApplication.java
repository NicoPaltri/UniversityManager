package application.mainapplication;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ExamsManagerApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(
                ExamsManagerApplication.class.getResource("/stages/MainStage.fxml")
        );

        Scene scene = new Scene(fxmlLoader.load(), 1000, 563);

        //css
        scene.getStylesheets().add(
                ExamsManagerApplication.class.getResource("/styles/generalStyleSheet.css").toExternalForm()
        );
        scene.getStylesheets().add(
                ExamsManagerApplication.class.getResource("/styles/specificMainStage.css").toExternalForm()
        );

        stage.setTitle("UniversityManager");
        stage.setScene(scene);
        stage.show();
    }

}
