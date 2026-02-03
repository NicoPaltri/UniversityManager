package examsmanager.examfactories;

import customexceptions.examformatexception.GradeFormatException;
import examsmanager.examtypes.GradedExam;

import java.time.LocalDate;

public class GradedExamFactory extends ExamFactory {
    public GradedExamFactory() {
    }

    public GradedExam createExam(String name, int weight, int grade, LocalDate date) {
        super.checkExam(name, weight, grade, date);
        grade = normalizeGrade(grade);

        return new GradedExam(name, weight, date, grade);
    }

    @Override
    protected void hook_isGradeOk(String name, int grade) {
        if (grade > 33 || grade < 18) {
            throw new GradeFormatException(name);
        }
    }

    private int normalizeGrade(int grade) {
        if (grade > 30) {
            grade = 30;
        }
        return grade;
    }
}
