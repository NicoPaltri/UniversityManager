package examsmanager.examfactories;

import customexceptions.ApplicationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

class TestExamFactory extends ExamFactory {}

class ExamFactoryTest {
    private final String nameOK = "prova";
    private final int weightOK = 9;
    private final LocalDate dateOk = LocalDate.of(2020, 11, 10);
    TestExamFactory testExamFactory = new TestExamFactory();

    // ---- WEIGHT TESTS ----

    @Test
    void weight_tooLow_throwsException() {
        assertThrows(ApplicationException.class, () -> {
            testExamFactory.checkExam(nameOK, 2, dateOk);
        });
    }

    @Test
    void weight_limitLow_doesNotThrow() {
        assertDoesNotThrow(() -> {
            testExamFactory.checkExam(nameOK, 3, dateOk);
        });
    }

    @Test
    void weight_limitHigh_doesNotThrow() {
        assertDoesNotThrow(() -> {
            testExamFactory.checkExam(nameOK, 12, dateOk);
        });
    }

    @Test
    void weight_tooHigh_throwsException() {
        assertThrows(ApplicationException.class, () -> {
            testExamFactory.checkExam(nameOK, 16, dateOk);
        });
    }

    // ---- DATE TESTS ----

    @Test
    void date_today_doesNotThrow() {
        assertDoesNotThrow(() -> {
            testExamFactory.checkExam(nameOK, weightOK, LocalDate.now());
        });
    }

    @Test
    void date_tomorrow_throwsException() {
        assertThrows(ApplicationException.class, () -> {
            testExamFactory.checkExam(nameOK, weightOK, LocalDate.now().plusDays(1));
        });
    }

    // ---- ALL OK ----

    @Test
    void allOk_doesNotThrow() {
        assertDoesNotThrow(() -> {
            testExamFactory.checkExam(nameOK, weightOK, dateOk);
        });
    }
}