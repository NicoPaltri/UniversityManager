package examsmanager.examfactories;

import customexceptions.ApplicationException;
import examsmanager.examtypes.Idoneita;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class IdoneitaFactoryTest {
    private final String nameOK = "prova";
    private final int weightOK = 9;
    private final Integer gradeOK = 22;
    private final LocalDate dateOk = LocalDate.of(2020, 11, 10);

    IdoneitaFactory idoneitaFactory = new IdoneitaFactory();

    @Test
    void grade_null_doesNotThrow() {
        assertDoesNotThrow(() -> {
            ExamCreationRequest data = new ExamCreationData(nameOK, weightOK, dateOk);

            Idoneita exam = idoneitaFactory.createExam(data);
        });
    }

    @Test
    void grade_notNull_throwsException() {
        assertThrows(ApplicationException.class, () -> {
            ExamCreationRequest data = new ExamCreationData(nameOK, weightOK, dateOk);
            data.withGrade(gradeOK);

            Idoneita exam = idoneitaFactory.createExam(data);
        });
    }
}