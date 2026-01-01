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

    // JavaFX needs to instantiate Application (Launcher is in javafx.graphics)
    exports application.mainapplication to javafx.graphics;

    exports dbmanager.settingsTable;

    // FXML reflection access (controllers)
    opens application.mainapplication to javafx.fxml;
    opens application.addbuttonapplication to javafx.fxml;
    opens application.removebuttonapplication to javafx.fxml;
    opens application.settingsbuttonapplication to javafx.fxml;
    opens application.modifybuttonapplication to javafx.fxml;
}
