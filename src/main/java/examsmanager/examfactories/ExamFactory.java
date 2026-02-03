package examsmanager.examfactories;

import customexceptions.dateexception.FutureDateException;
import customexceptions.examformatexception.WeightFormatException;
import java.time.LocalDate;

public abstract class ExamFactory {
    public ExamFactory() {
    }


    protected void checkExam(String name, int weight, LocalDate date) {
        isWeightOk(name, weight);
        isDateOk(name, date);
    }

    protected void checkExam(String name, int weight, int grade, LocalDate date) {
        checkExam(name, weight, date);

        hook_isGradeOk(name, grade);
    }


    private void isWeightOk(String name, int weight) {
        if (weight < 3 || weight > 15) {
            throw new WeightFormatException(name);
        }
    }

    private void isDateOk(String name, LocalDate date) {
        if(date.isAfter(LocalDate.now())){
            throw new FutureDateException(name,date);
        }
    }


    protected void hook_isGradeOk(String name, int grade) {}
}
