package examsmanager.examtypes;

import customexceptions.examexceptions.UnknownExamTypeException;
import examsmanager.examfactories.ExamCreationRequest;
import examsmanager.examfactories.ExamFactory;
import examsmanager.examfactories.GradedExamFactory;
import examsmanager.examfactories.IdoneitaFactory;

public enum ExamTypologies {

    GradedExam(
            "GRADED",
            data -> new GradedExamFactory().createExam(data)
    ),

    Idoneita(
            "IDONEITA",
            data -> new IdoneitaFactory().createExam(data)
    );

    private final String examTypology;
    private final ExamCreator<? extends Exam> creator;

    ExamTypologies(String name, ExamCreator<? extends Exam> creator) {
        this.examTypology = name;
        this.creator = creator;
    }

    public String getExamTypology() {
        return examTypology;
    }

    public Exam createExam(ExamCreationRequest data) {
        return creator.createExam(data);
    }

    public static ExamTypologies fromType(String name, String type) {
        for (ExamTypologies typology : values()) {
            if (typology.examTypology.equals(type)) {
                return typology;
            }
        }
        throw new UnknownExamTypeException(name, type);
    }
}
