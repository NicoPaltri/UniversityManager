package settingsmanager;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Setting {
    StringProperty name;
    IntegerProperty value;

    public Setting(String name, int value) {
        this.name = new SimpleStringProperty(name);
        this.value = new SimpleIntegerProperty(value);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public IntegerProperty valueProperty() {
        return value;
    }

    public String getName() {
        return name.getName();
    }

    public int getValue() {
        return value.getValue();
    }

    @Override
    public String toString() {
        return "Il setting " + this.getName() + ", ha value " + this.getValue();
    }
}
