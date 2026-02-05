package application.applicationutils.openwindowmanager;

import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.function.Consumer;

public final class WindowRequest<C> {
    private final String windowName;
    private final String windowPath;
    private String specificCssPath;
    private Pane mainPane;

    private double width = 750;
    private double height = 500;

    private Stage stage = null;

    private boolean modal = true;
    private boolean resizable = true;

    private java.util.function.Consumer<C> controllerInitializer;
    private Runnable onClose;

    public WindowRequest(String windowName, String fxmlPath) {
        this.windowName = windowName;
        this.windowPath = fxmlPath;
    }

    public WindowRequest<C> withSize(double width, double height) {
        this.width = width;
        this.height = height;
        return this;
    }

    public WindowRequest<C> withOverrideCss(String specificCssPath) {
        this.specificCssPath = specificCssPath;
        return this;
    }

    public WindowRequest<C> withOwnerPane(Pane mainPane) {
        this.mainPane = mainPane;
        return this;
    }

    public WindowRequest<C> withModal(boolean modal) {
        this.modal = modal;
        return this;
    }

    public WindowRequest<C> withResizable(boolean resizable) {
        this.resizable = resizable;
        return this;
    }

    public WindowRequest<C> withControllerInitializer(java.util.function.Consumer<C> controllerInitializer) {
        this.controllerInitializer = controllerInitializer;
        return this;
    }

    public WindowRequest<C> withOnClose(Runnable runnable) {
        this.onClose = runnable;
        return this;
    }

    public WindowRequest<C> withStage(Stage stage) {
        this.stage = stage;
        return this;
    }


    public String getWindowName() {
        return windowName;
    }

    public String getWindowPath() {
        return windowPath;
    }

    public String getSpecificCssPath() {
        return specificCssPath;
    }

    public Pane getMainPane() {
        return mainPane;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public Stage getStage() {
        return stage;
    }

    public boolean isModal() {
        return modal;
    }

    public boolean isResizable() {
        return resizable;
    }

    public Consumer<C> getControllerInitializer() {
        return controllerInitializer;
    }

    public Runnable getOnClose() {
        return onClose;
    }
}

