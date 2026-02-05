package examsmanager.examfactories;

import customexceptions.ApplicationException;
import examsmanager.examtypes.Exam;
import javafx.beans.property.StringProperty;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.Optional;

class TestExam extends Exam {
    private int grade;

    public TestExam(String name, int weight, int grade, LocalDate date) {
        super(name, weight, date);
        this.grade = grade;
    }

    @Override
    public Optional<Integer> getGrade() {
        return Optional.of(grade);
    }

    @Override
    public String getType() {
        return "";
    }

    @Override
    public StringProperty gradeProperty() {
        return null;
    }
}

class TestExamFactory extends ExamFactory<TestExam> {
    @Override
    protected TestExam doCreate(ExamCreationData data) {
        return new TestExam(data.getName(), data.getWeight(), data.getGrade(), data.getDate());
    }
}

class ExamFactoryTest {
    private final String nameOK = "prova";
    private final int weightOK = 9;
    private final Integer gradeOK = 22;
    private final LocalDate dateOk = LocalDate.of(2020, 11, 10);

    TestExamFactory testExamFactory = new TestExamFactory();

    // ---- WEIGHT TESTS ----

    @Test
    void weight_tooLow_throwsException() {
        assertThrows(ApplicationException.class, () -> {
            ExamCreationData data = new ExamCreationData(nameOK, 2, dateOk);
            data.withGrade(gradeOK);

            TestExam exam = testExamFactory.createExam(data);
        });
    }

    @Test
    void weight_limitLow_doesNotThrow() {
        assertDoesNotThrow(() -> {
            ExamCreationData data = new ExamCreationData(nameOK, 3, dateOk);
            data.withGrade(gradeOK);

            TestExam exam = testExamFactory.createExam(data);
        });
    }

    @Test
    void weight_limitHigh_doesNotThrow() {
        assertDoesNotThrow(() -> {
            ExamCreationData data = new ExamCreationData(nameOK, 15, dateOk);
            data.withGrade(gradeOK);

            TestExam exam = testExamFactory.createExam(data);
        });
    }

    @Test
    void weight_tooHigh_throwsException() {
        assertThrows(ApplicationException.class, () -> {
            ExamCreationData data = new ExamCreationData(nameOK, 16, dateOk);
            data.withGrade(gradeOK);

            TestExam exam = testExamFactory.createExam(data);
        });
    }

    // ---- DATE TESTS ----

    @Test
    void date_today_doesNotThrow() {
        assertDoesNotThrow(() -> {
            ExamCreationData data = new ExamCreationData(nameOK, weightOK, LocalDate.now());
            data.withGrade(gradeOK);

            TestExam exam = testExamFactory.createExam(data);
        });
    }

    @Test
    void date_tomorrow_throwsException() {
        assertThrows(ApplicationException.class, () -> {
            ExamCreationData data = new ExamCreationData(nameOK, weightOK, LocalDate.now().plusDays(1));
            data.withGrade(gradeOK);

            TestExam exam = testExamFactory.createExam(data);
        });
    }

    // ---- GRADE TESTS ----

    @Test
    void grade_tooLow_throwsException() {
        assertThrows(ApplicationException.class, () -> {
            ExamCreationData data = new ExamCreationData(nameOK, weightOK, dateOk);
            data.withGrade(17);

            TestExam exam = testExamFactory.createExam(data);
        });
    }

    @Test
    void grade_limitLow_doesNotThrow() {
        assertDoesNotThrow(() -> {
            ExamCreationData data = new ExamCreationData(nameOK, weightOK, dateOk);
            data.withGrade(18);

            TestExam exam = testExamFactory.createExam(data);
        });
    }

    @Test
    void grade_limitHigh_doesNotThrow() {
        assertDoesNotThrow(() -> {
            ExamCreationData data = new ExamCreationData(nameOK, weightOK, dateOk);
            data.withGrade(33);

            TestExam exam = testExamFactory.createExam(data);
        });
    }

    @Test
    void grade_tooHigh_throwsException() {
        assertThrows(ApplicationException.class, () -> {
            ExamCreationData data = new ExamCreationData(nameOK, weightOK, dateOk);
            data.withGrade(34);

            TestExam exam = testExamFactory.createExam(data);
        });
    }

    // ---- ALL OK ----

    @Test
    void allOk_doesNotThrow() {
        assertDoesNotThrow(() -> {
            assertDoesNotThrow(() -> {
                ExamCreationData data = new ExamCreationData(nameOK, weightOK, dateOk);
                data.withGrade(gradeOK);

                TestExam exam = testExamFactory.createExam(data);
            });
        });
    }
}