package universitymanager.examtypes;

import application.mainapplication.Launcher;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;

public class Idoneita extends Exam {
    public Idoneita(String name, int weight, LocalDate date) {
        super(name, weight, date);
    }

    @Override
    public String getType() {
        return ExamTypologies.Idoneita.getExamTypology();
    }

    //UI
    @Override
    public StringProperty gradeProperty() {
        return new SimpleStringProperty("-");
    }


}
