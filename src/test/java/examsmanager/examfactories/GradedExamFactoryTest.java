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
    void grade_tooLow_throwsException() {
        assertThrows(ApplicationException.class, () -> {
            ExamCreationData data = new ExamCreationData(nameOK, weightOK, dateOk);
            data.withGrade(17);

            GradedExam exam = gradedExamFactory.createExam(data);
        });
    }

    @Test
    void grade_limitLow_doesNotThrow() {
        assertDoesNotThrow(() -> {
            ExamCreationData data = new ExamCreationData(nameOK, weightOK, dateOk);
            data.withGrade(18);

            GradedExam exam = gradedExamFactory.createExam(data);
        });
    }

    @Test
    void grade_limitHigh_doesNotThrow() {
        assertDoesNotThrow(() -> {
            ExamCreationData data = new ExamCreationData(nameOK, weightOK, dateOk);
            data.withGrade(33);

            GradedExam exam = gradedExamFactory.createExam(data);
        });
    }

    @Test
    void grade_tooHigh_throwsException() {
        assertThrows(ApplicationException.class, () -> {
            ExamCreationData data = new ExamCreationData(nameOK, weightOK, dateOk);
            data.withGrade(34);

            GradedExam exam = gradedExamFactory.createExam(data);
        });
    }
    
    @Test
    public void grade_notNull_doesNotThrow() {
        ExamCreationData data = new ExamCreationData(nameOK, weightOK, dateOk);
        data.withGrade(gradeOK);

        GradedExam exam = gradedExamFactory.createExam(data);
    }

    @Test
    public void grade_null_throwsException() {
        assertThrows(ApplicationException.class, () -> {
            ExamCreationData data = new ExamCreationData(nameOK, weightOK, dateOk);

            GradedExam exam = gradedExamFactory.createExam(data);
        });
    }
}