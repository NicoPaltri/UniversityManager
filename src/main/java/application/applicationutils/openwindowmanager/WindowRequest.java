package application.applicationutils.openwindowmanager;

import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public final class WindowRequest<C> {
    public String windowName;
    public String windowPath;
    public String specificCssPath;
    public Pane mainPane;

    public double width = 750;
    public double height = 500;

    public Stage stage = null;

    public boolean modal = true;
    public boolean resizable = true;

    public java.util.function.Consumer<C> controllerInitializer;
    public Runnable onClose;

    public WindowRequest(String windowName, String fxmlPath) {
        this.windowName = windowName;
        this.windowPath = fxmlPath;
    }

    public WindowRequest<C> size(double width, double height) {
        this.width = width;
        this.height = height;
        return this;
    }

    public WindowRequest<C> overrideCss(String specificCssPath) {
        this.specificCssPath = specificCssPath;
        return this;
    }

    public WindowRequest<C> owner(Pane mainPane) {
        this.mainPane = mainPane;
        return this;
    }

    public WindowRequest<C> modal(boolean modal) {
        this.modal = modal;
        return this;
    }

    public WindowRequest<C> resizable(boolean resizable) {
        this.resizable = resizable;
        return this;
    }

    public WindowRequest<C> controllerInitializer(java.util.function.Consumer<C> controllerInitializer) {
        this.controllerInitializer = controllerInitializer;
        return this;
    }

    public WindowRequest<C> onClose(Runnable runnable) {
        this.onClose = runnable;
        return this;
    }

    public WindowRequest<C> useStage(Stage stage) {
        this.stage = stage;
        return this;
    }
}

