package examsmanager.examfactories;

import customexceptions.examexceptions.GradeFormatException;
import customexceptions.examexceptions.NullGradeForGradedExamException;
import examsmanager.examtypes.GradedExam;

public class GradedExamFactory extends ExamFactory<GradedExam> {
    @Override
    protected GradedExam doCreate(ExamCreationRequest data) {
        return new GradedExam(
                data.getName(),
                data.getWeight(),
                data.getDate(),
                data.getGrade());
    }

    @Override
    protected void validateSpecific(ExamCreationRequest data) {
        validateGrade(data);
    }

    private void validateGrade(ExamCreationRequest data) {
        if (!data.hasGrade()) {
            throw new NullGradeForGradedExamException(data.getName());
        }

        int grade = data.getGrade();
        if (grade < 18 || grade > 33) {
            throw new GradeFormatException(data.getName());
        }

        if (grade > 30) {
            data.withGrade(30);
        }
    }
}
