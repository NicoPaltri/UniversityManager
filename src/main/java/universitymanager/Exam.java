package universitymanager;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Objects;

public abstract class Exam {
    private final String name;
    private final int weight;
    private String date;

    public Exam(String name, int weight, String date) {
        this.name = name;
        this.weight = weight;
        this.date = date;
    }


    public String getName() {
        return name;
    }

    public int getWeight() {
        return weight;
    }

    public String getDate() {
        return date;
    }


    public void setDate(String newDate) {
        this.date = newDate;
    }


    public abstract String getType(); //a way to "know" the class without instanceof


    //UI
    public StringProperty nameProperty() {
        return new SimpleStringProperty(name);
    }

    public IntegerProperty weightProperty() {
        return new SimpleIntegerProperty(weight);
    }

    public StringProperty dateProperty() {
        return new SimpleStringProperty(date);
    }

    public abstract StringProperty gradeProperty();


    @Override
    public String toString() {
        return this.getType() + "[" +
                " name=" + this.getName() +
                " weight=" + this.getWeight() +
                " grade=" + this.gradeProperty().get() +
                " date=" + this.getDate() +
                " type=" + this.getType() +
                "].";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Exam exam = (Exam) o;
        return Objects.equals(getName(), exam.getName());
    }


    @Override
    public int hashCode() {
        return Objects.hashCode(this.getName());
    }
}
