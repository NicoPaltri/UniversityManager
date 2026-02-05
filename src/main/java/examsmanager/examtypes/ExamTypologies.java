package examsmanager.examtypes;

import customexceptions.examexceptions.UnknownExamTypeException;
import examsmanager.examfactories.ExamCreationData;
import examsmanager.examfactories.GradedExamFactory;
import examsmanager.examfactories.IdoneitaFactory;

import java.util.function.Function;

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
    private final Function<ExamCreationData, Exam> creator;

    ExamTypologies(String examTypology, Function<ExamCreationData, Exam> creator) {
        this.examTypology = examTypology;
        this.creator = creator;
    }

    public String getExamTypology() {
        return examTypology;
    }

    public Exam create(ExamCreationData data) {
        return creator.apply(data);
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
