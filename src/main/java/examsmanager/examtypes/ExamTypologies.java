package examsmanager.examtypes;

import customexceptions.examformatexception.UnknownExamTypeException;

public enum ExamTypologies {
    GradedExam("GRADED"),
    Idoneita("IDONEITA");

    private final String examTypology;

    ExamTypologies(String examTypology) {
        this.examTypology = examTypology;
    }

    public String getExamTypology() {
        return examTypology;
    }

    public static ExamTypologies fromType(String name, String type) {
        for (ExamTypologies typology : ExamTypologies.values()) {
            if (typology.getExamTypology().equals(type)) {
                return typology;
            }
        }

        throw new UnknownExamTypeException(name, type);
    }
}
