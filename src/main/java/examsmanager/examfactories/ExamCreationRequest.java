package examsmanager.examfactories;

import java.time.LocalDate;

public interface ExamCreationRequest {

    String getName();

    int getWeight();

    LocalDate getDate();


    boolean hasGrade();

    ExamCreationRequest withGrade(int grade);

    Integer getGrade();
}
