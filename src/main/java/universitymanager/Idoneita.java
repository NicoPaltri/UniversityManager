package universitymanager;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Idoneita extends Exam{
    public Idoneita(String name, int weight, String date) {
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
