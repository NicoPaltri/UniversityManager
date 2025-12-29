module universitymanager {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.graphics;
    requires org.xerial.sqlitejdbc;
    requires java.desktop;
    requires javafx.base;
    requires commons.math3;
    requires universitymanager;

    opens application.mainapplication to javafx.fxml;
    exports application.mainapplication;

    opens application.addbuttonapplication to javafx.fxml;
    exports application.addbuttonapplication;

    opens application.removebuttonapplication to javafx.fxml;
    exports application.removebuttonapplication;

    opens universitymanager;
    exports universitymanager;
}

