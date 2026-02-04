package examsmanager.examfactories;

import customexceptions.dateexception.FutureDateException;
import customexceptions.examformatexception.GradeFormatException;
import customexceptions.examformatexception.WeightFormatException;
import examsmanager.examtypes.Exam;

import java.time.LocalDate;

public abstract class ExamFactory<E extends Exam> {

    public final E createExam(ExamCreationData data) {
        checkExam(data);
        validateSpecific(data);

        return doCreate(data);
    }

    protected abstract E doCreate(ExamCreationData data);

    protected void checkExam(ExamCreationData data) {
        validateWeight(data.getName(), data.getWeight());
        validateDate(data.getName(), data.getDate());

        if (data.hasGrade()) {
            validateGrade(data.getName(), data.getGrade());

            data.withGrade(normalizeGrade(data.getGrade()));
        }
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

    private void validateGrade(String name, int grade) {
        if (grade < 18 || grade > 33) {
            throw new GradeFormatException(name);
        }
    }

    private int normalizeGrade(int grade) {
        return grade > 30 ? 30 : grade;
    }
}

