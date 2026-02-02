package application.applicationcontrollers.mainapplication;

import application.applicationutils.openwindowmanager.OpenWindowUtils;
import application.applicationutils.openwindowmanager.WindowRequest;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class ExamsManagerApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        WindowRequest<Object> windowRequest = new WindowRequest<>(
                "University Manager",
                "/stages/MainStage.fxml"
        );
        windowRequest.overrideCss("/styles/specificMainStage.css");
        windowRequest.useStage(stage);
        windowRequest.size(1000, 563);
        windowRequest.modal(false);

        OpenWindowUtils openWindowUtils = new OpenWindowUtils();
        openWindowUtils.openNewWindow(windowRequest);
    }

}
