package examsmanager.examtypes;

import customexceptions.ApplicationException;
import examsmanager.examfactories.ExamCreationData;
import examsmanager.examfactories.GradedExamFactory;
import examsmanager.examfactories.IdoneitaFactory;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ExamTest {
    private final String nameOneOK = "prova";
    private final String nameTwoOK = "banana";

    private final int weightOneOK = 9;
    private final int weightTwoOK = 10;

    private final Integer gradeOK = 22;
    private final LocalDate dateOk = LocalDate.of(2020, 11, 10);

    IdoneitaFactory idoneitaFactory = new IdoneitaFactory();
    GradedExamFactory gradedExamFactory = new GradedExamFactory();

    // ---- EQUALS TEST ----

    @Test
    void differentClass_sameName_notEquals() {
        ExamCreationData dataIdoneita = new ExamCreationData(nameOneOK, weightOneOK, dateOk);

        ExamCreationData dataGradedExam = new ExamCreationData(nameOneOK, weightOneOK, dateOk);
        dataGradedExam.withGrade(gradeOK);

        Idoneita idoneita = idoneitaFactory.createExam(dataIdoneita);
        GradedExam gradedExam = gradedExamFactory.createExam(dataGradedExam);

        assertNotEquals(idoneita, gradedExam);
    }

    @Test
    void sameClass_differentName_notEqual() {
        ExamCreationData dataIdoneitaOne = new ExamCreationData(nameOneOK, weightOneOK, dateOk);
        ExamCreationData dataIdoneitaTwo = new ExamCreationData(nameTwoOK, weightOneOK, dateOk);

        Idoneita idoneitaOne = idoneitaFactory.createExam(dataIdoneitaOne);
        Idoneita idoneitaTwo = idoneitaFactory.createExam(dataIdoneitaTwo);

        assertNotEquals(idoneitaOne, idoneitaTwo);
    }

    @Test
    void nullParameter_notEqual() {
        ExamCreationData dataIdoneitaOne = new ExamCreationData(nameOneOK, weightOneOK, dateOk);

        Idoneita idoneitaOne = idoneitaFactory.createExam(dataIdoneitaOne);

        assertNotEquals(null, idoneitaOne);
    }

    @Test
    void sameClass_sameName_equal() {
        ExamCreationData dataIdoneitaOne = new ExamCreationData(nameOneOK, weightOneOK, dateOk);
        ExamCreationData dataIdoneitaTwo = new ExamCreationData(nameOneOK, weightTwoOK, dateOk);

        Idoneita idoneitaOne = idoneitaFactory.createExam(dataIdoneitaOne);
        Idoneita idoneitaTwo = idoneitaFactory.createExam(dataIdoneitaTwo);

        assertEquals(idoneitaOne, idoneitaTwo);
    }

    // ---- HASH TESTS ----

    @Test
    void differentName_notEqualHash() {
        ExamCreationData dataIdoneitaOne = new ExamCreationData(nameOneOK, weightOneOK, dateOk);
        ExamCreationData dataIdoneitaTwo = new ExamCreationData(nameTwoOK, weightOneOK, dateOk);

        Idoneita idoneitaOne = idoneitaFactory.createExam(dataIdoneitaOne);
        Idoneita idoneitaTwo = idoneitaFactory.createExam(dataIdoneitaTwo);

        assertNotEquals(idoneitaOne.hashCode(), idoneitaTwo.hashCode());
    }

    @Test
    void sameName_equalHash() {
        ExamCreationData dataIdoneitaOne = new ExamCreationData(nameOneOK, weightOneOK, dateOk);
        ExamCreationData dataIdoneitaTwo = new ExamCreationData(nameOneOK, weightTwoOK, dateOk);

        Idoneita idoneitaOne = idoneitaFactory.createExam(dataIdoneitaOne);
        Idoneita idoneitaTwo = idoneitaFactory.createExam(dataIdoneitaTwo);

        assertEquals(idoneitaOne.hashCode(), idoneitaTwo.hashCode());
    }

}