package universitymanager.examtypes;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;

public class GradedExam extends Exam {
    private final int grade;

    public GradedExam(String name, int weight, LocalDate date, int grade) {
        super(name, weight, date);
        this.grade = grade;
    }

    public int getGrade() {
        return grade;
    }

    @Override
    public String getType() {
        return ExamTypologies.GradedExam.getExamTypology();
    }

    //UI
    @Override
    public StringProperty gradeProperty() {
        return new SimpleStringProperty(String.valueOf(grade));
    }
}
