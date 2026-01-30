package universitymanager.examtypes;

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
}
