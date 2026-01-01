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

    public StringProperty getNameProperty() {
        return name;
    }

    public IntegerProperty getValueProperty() {
        return value;
    }

    public String getName() {
        return name.get();
    }

    public int getValue() {
        return value.get();
    }

    @Override
    public String toString() {
        return "Il setting " + this.getName() + ", ha value " + this.getValue();
    }
}
