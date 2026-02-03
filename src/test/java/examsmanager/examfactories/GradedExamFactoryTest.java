package examsmanager.examfactories;

import customexceptions.ApplicationException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class GradedExamFactoryTest {
    private final String nameOK = "prova";
    private final int weightOK = 9;
    private final LocalDate dateOK = LocalDate.of(2020, 11, 10);

    GradedExamFactory gradedExamFactory = new GradedExamFactory();

    @Test
    void grade_tooLow_throwsException() {
        assertThrows(ApplicationException.class, () -> {
            gradedExamFactory.checkExam(nameOK, weightOK, 17, dateOK);
        });
    }

    @Test
    void grade_limitLow_doesNotThrow() {
        assertDoesNotThrow(() -> {
            gradedExamFactory.checkExam(nameOK, weightOK, 18, dateOK);
        });
    }

    @Test
    void grade_limitHigh_doesNotThrow() {
        assertDoesNotThrow(() -> {
            gradedExamFactory.checkExam(nameOK, weightOK, 33, dateOK);
        });
    }

    @Test
    void grade_tooHigh_throwsException() {
        assertThrows(ApplicationException.class, () -> {
            gradedExamFactory.checkExam(nameOK, weightOK, 34, dateOK);
        });
    }

}