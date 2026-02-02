module universitymanager {
    // JavaFX
    requires javafx.controls;
    requires javafx.fxml;

    // Desktop
    requires java.desktop;

    // DB
    requires java.sql;
    requires org.xerial.sqlitejdbc;

    // Math
    requires commons.math3;
    requires javafx.base;
    requires javafx.graphics;

    // JavaFX needs to instantiate Application (Launcher is in javafx.graphics)
    exports application.applicationcontrollers.mainapplication to javafx.graphics;

    exports dbmanager.settingsTable;
    exports universitymanager;
    exports settingsmanager;

    // FXML reflection access (controllers)
    opens application.applicationcontrollers.mainapplication to javafx.fxml;
    opens application.applicationcontrollers.addbuttonapplication to javafx.fxml;
    opens application.applicationcontrollers.removebuttonapplication to javafx.fxml;
    opens application.applicationcontrollers.settingsbuttonapplication to javafx.fxml;
    opens application.applicationcontrollers.modifybuttonapplication to javafx.fxml;
    exports universitymanager.examfactories;
    exports universitymanager.examtypes;
    opens application.applicationutils.openwindowmanager to javafx.fxml;
    opens application.applicationutils to javafx.fxml;
}
