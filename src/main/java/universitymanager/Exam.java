package universitymanager;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Objects;

public class Exam {
    private final StringProperty nameFX;
    private final IntegerProperty weightFX;
    private final IntegerProperty gradeFX;
    private final StringProperty dateFX;

    public Exam(String name, String date, int grade, int weight) {
        this.nameFX = new SimpleStringProperty(name);
        this.weightFX = new SimpleIntegerProperty(weight);
        this.gradeFX = new SimpleIntegerProperty(grade);
        this.dateFX = new SimpleStringProperty(date);
    }

    public String getName() {
        return nameFX.get();
    }

    public String getDate() {
        return dateFX.get();
    }

    public int getGrade() {
        return gradeFX.get();
    }

    public int getWeight() {
        return weightFX.get();
    }

    public StringProperty nameProperty() {
        return nameFX;
    }

    public IntegerProperty weightProperty() {
        return weightFX;
    }

    public IntegerProperty gradeProperty() {
        return gradeFX;
    }

    public StringProperty dateProperty() {
        return dateFX;
    }

    public void setDate(String newDate) {
        this.dateFX.set(newDate);
    }

    @Override
    public String toString() {
        return "name: " + this.getName() +
                ", weight: " + this.getWeight() +
                ", grade: " + this.getGrade() +
                ", date: " + this.getDate() + ".";
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Exam exam = (Exam) o;
        return Objects.equals(nameFX, exam.nameFX);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(nameFX);
    }
}
