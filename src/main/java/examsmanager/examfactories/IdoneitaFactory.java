package examsmanager.examfactories;

import customexceptions.examexceptions.GradeNotNullForIdoneitaException;
import examsmanager.examtypes.Idoneita;

public class IdoneitaFactory extends ExamFactory<Idoneita> {
    @Override
    protected Idoneita doCreate(ExamCreationData data) {
        return new Idoneita(
                data.getName(),
                data.getWeight(),
                data.getDate());
    }

    @Override
    protected void validateSpecific(ExamCreationData data) {
        if (data.hasGrade()) {
            throw new GradeNotNullForIdoneitaException(data.getName());
        }
    }
}
