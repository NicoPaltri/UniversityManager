package examsmanager.examfactories;

import customexceptions.dateexception.FutureDateException;
import customexceptions.examexceptions.GradeFormatException;
import customexceptions.examexceptions.WeightFormatException;
import examsmanager.examtypes.Exam;
import examsmanager.examtypes.ExamCreator;

import java.time.LocalDate;

public abstract class ExamFactory<T extends Exam> implements ExamCreator<T> {

    public final T createExam(ExamCreationRequest data) {
        checkData(data);
        validateSpecific(data);

        return doCreate(data);
    }

    protected abstract T doCreate(ExamCreationRequest data);

    protected void checkData(ExamCreationRequest data) {
        validateWeight(data.getName(), data.getWeight());
        validateDate(data.getName(), data.getDate());
    }


    protected void validateSpecific(ExamCreationRequest data) {
    }


    private void validateWeight(String name, int weight) {
        if (weight < 3 || weight > 15) {
            throw new WeightFormatException(name);
        }
    }

    private void validateDate(String name, LocalDate date) {
        if (date.isAfter(LocalDate.now())) {
            throw new FutureDateException(name, date);
        }
    }

}

