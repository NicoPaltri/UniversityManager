package examsmanager.examfactories;

import customexceptions.ApplicationException;
import examsmanager.examtypes.GradedExam;
import examsmanager.examtypes.Idoneita;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class GradedExamFactoryTest {
    private final String nameOK = "prova";
    private final int weightOK = 9;
    private final Integer gradeOK = 22;
    private final LocalDate dateOk = LocalDate.of(2020, 11, 10);

    GradedExamFactory gradedExamFactory = new GradedExamFactory();

    @Test
    public void grade_notNull_doesNotThrow() {
        ExamCreationData data = new ExamCreationData(nameOK, weightOK, dateOk);
        data.withGrade(gradeOK);

        GradedExam exam = gradedExamFactory.createExam(data);
    }
}