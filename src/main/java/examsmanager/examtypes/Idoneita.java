package examsmanager.examtypes;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;
import java.util.Optional;

public class Idoneita extends Exam {
    public Idoneita(String name, int weight, LocalDate date) {
        super(name, weight, date);
    }

    @Override
    public String getType() {
        return ExamTypologies.Idoneita.getExamTypology();
    }

    @Override
    public Optional<Integer> getGrade() {
        return Optional.empty();
    }

    //UI
    @Override
    public StringProperty gradeProperty() {
        return new SimpleStringProperty("-");
    }


}
