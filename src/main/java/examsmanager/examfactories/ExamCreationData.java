package examsmanager.examfactories;

import java.time.LocalDate;

public class ExamCreationData implements ExamCreationRequest {

    private final String name;
    private final int weight;
    private final LocalDate date;

    private Integer grade = null;

    public ExamCreationData(String name, int weight, LocalDate date) {
        this.name = name;
        this.weight = weight;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public int getWeight() {
        return weight;
    }

    public LocalDate getDate() {
        return date;
    }


    public ExamCreationRequest withGrade(int grade) {
        this.grade = grade;
        return this;
    }

    public boolean hasGrade() {
        return grade != null;
    }

    public Integer getGrade() {
        return grade;
    }
}

