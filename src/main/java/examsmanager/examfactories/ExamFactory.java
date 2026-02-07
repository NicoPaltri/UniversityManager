package examsmanager.examfactories;

import customexceptions.dateexception.FutureDateException;
import customexceptions.examexceptions.GradeFormatException;
import customexceptions.examexceptions.WeightFormatException;
import examsmanager.examtypes.Exam;

import java.time.LocalDate;

public abstract class ExamFactory<E extends Exam> {

    public final E createExam(ExamCreationData data) {
        checkData(data);
        validateSpecific(data);

        return doCreate(data);
    }

    protected abstract E doCreate(ExamCreationData data);

    protected void checkData(ExamCreationData data) {
        validateWeight(data.getName(), data.getWeight());
        validateDate(data.getName(), data.getDate());
    }


    protected void validateSpecific(ExamCreationData data) {
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

