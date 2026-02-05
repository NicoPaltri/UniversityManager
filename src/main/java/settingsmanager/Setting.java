package settingsmanager;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Setting {
    String name;
    int value;

    public Setting(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Il setting " + this.getName() + ", ha value " + this.getValue();
    }

    //UI
    public StringProperty getNameProperty() {
        return new SimpleStringProperty(getName());
    }

    public IntegerProperty getValueProperty() {
        return new SimpleIntegerProperty(getValue());
    }

}
