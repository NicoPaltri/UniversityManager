package universitymanager;

import customexceptions.dateexception.FutureDateException;
import customexceptions.dateexception.InvalidDateFormatException;
import customexceptions.examformatexception.GradeFormatException;
import customexceptions.examformatexception.WeightFormatException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ExamFactory {
    public ExamFactory() {
    }

    public Exam createExam(String name, int weight, int grade, String date) {

        isWeightOk(name, weight);
        isGradeOk(name, grade);
        grade = normalizeGrade(grade);
        isDateOk(name, date);

        Exam exam = new Exam(name, date, grade, weight);

        System.out.println("Esame creato con successo: " + exam.toString());

        return exam;
    }

    private void isWeightOk(String name, int weight) {
        if (weight < 3 || weight > 15) {
            throw new WeightFormatException(name);
        }
    }

    private void isGradeOk(String name, int grade) {
        if (grade > 33 || grade < 18) {
            throw new GradeFormatException(name);
        }
    }

    private int normalizeGrade(int grade) {
        if (grade > 30) {
            grade = 30;
        }
        return grade;
    }

    private void isDateOk(String name, String date) {
        try {
            LocalDate correctDate = LocalDate.parse(date, DateTimeFormatter.BASIC_ISO_DATE);

            if (correctDate.isAfter(LocalDate.now())) {
                throw new FutureDateException(name, correctDate.toString());
            }

        } catch (DateTimeParseException e) {
            throw new InvalidDateFormatException(name, date);
        }
    }
}
