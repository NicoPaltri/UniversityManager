package examsmanager.examtypes;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

public abstract class Exam {
    private final String name;
    private final int weight;
    private LocalDate date;

    public Exam(String name, int weight, LocalDate date) {
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

    public LocalDate getDate() {
        return date;
    }


    public void setDate(LocalDate newDate) {
        this.date = newDate;
    }


    public abstract String getType();

    public abstract Optional<Integer> getGrade();


    @Override
    public String toString() {
        return this.getType() + "[" +
                " name=" + this.getName() +
                ", weight=" + this.getWeight() +
                ", grade=" + this.gradeProperty().get() +
                ", date=" + this.getDate() +
                ", type=" + this.getType() +
                "]";
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

    //UI
    public StringProperty nameProperty() {
        return new SimpleStringProperty(name);
    }

    public IntegerProperty weightProperty() {
        return new SimpleIntegerProperty(weight);
    }

    public StringProperty dateProperty() {
        String stringedDate = String.valueOf(this.getDate());
        return new SimpleStringProperty(stringedDate);
    }

    public abstract StringProperty gradeProperty();

}
