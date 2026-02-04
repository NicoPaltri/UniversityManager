package examsmanager.examfactories;

import java.time.LocalDate;

public class ExamCreationData {

    private final String name;
    private final int weight;
    private final LocalDate date;

    private Integer grade = null;

    public ExamCreationData(String name, int weight, LocalDate date) {
        this.name = name;
        this.weight = weight;
        this.date = date;
    }

    public ExamCreationData withGrade(int grade) {
        this.grade = grade;
        return this;
    }

    public boolean hasGrade() {
        return grade != null;
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

    public Integer getGrade() {
        return grade;
    }
}

