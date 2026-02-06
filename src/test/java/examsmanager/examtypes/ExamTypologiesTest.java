package examsmanager.examtypes;

import customexceptions.ApplicationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExamTypologiesTest {

    // ---- FROM TYPE TESTS----
    @Test
    void existingType_doesNotThrow() {
        assertDoesNotThrow(() -> {
            ExamTypologies.fromType(
                    "testName",
                    ExamTypologies.GradedExam.getExamTypology()
            );
        });
    }

    @Test
    void nonExistingType_throwException() {
        assertThrows(ApplicationException.class, () -> {
            ExamTypologies.fromType(
                    "testName",
                    "NoWayAnExamTypologyExistsWithThisName");
        });
    }

}