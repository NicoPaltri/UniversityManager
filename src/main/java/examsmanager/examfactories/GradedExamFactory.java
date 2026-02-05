package examsmanager.examfactories;

import customexceptions.examexceptions.NullGradeForGradedExamException;
import examsmanager.examtypes.GradedExam;

public class GradedExamFactory extends ExamFactory<GradedExam> {
    @Override
    protected GradedExam doCreate(ExamCreationData data) {
        return new GradedExam(
                data.getName(),
                data.getWeight(),
                data.getDate(),
                data.getGrade());
    }

    @Override
    protected void validateSpecific(ExamCreationData data) {
        if (!data.hasGrade()) {
            throw new NullGradeForGradedExamException(data.getName());
        }
    }

}
