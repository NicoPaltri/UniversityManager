package application;

import customexceptions.dateexception.InvalidDateFormatException;
import universitymanager.examtypes.Exam;
import universitymanager.examtypes.GradedExam;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class ExamUtils {
    public ExamUtils() {
    }

    public static String makeThisTwoDigits(String text) {
        if (text.length() == 1) {
            return "0" + text;
        }

        return text;
    }

    public static List<GradedExam> filterGradedExamsFromExamList(List<Exam> exams) {
        return exams.stream()
                .filter(e -> e instanceof GradedExam)
                .map(e -> (GradedExam) e)
                .collect(Collectors.toList());
    }

    public static LocalDate buildStandardDate(String year, String month, String day) {
        try {
            day = ExamUtils.makeThisTwoDigits(day);
            month = ExamUtils.makeThisTwoDigits(month);

            int intDay = Integer.parseInt(day);
            int intMonth = Integer.parseInt(month);
            int intYear = Integer.parseInt(year);

            return LocalDate.of(intYear, intMonth, intDay);
        } catch (NumberFormatException | DateTimeException e) {
            throw new InvalidDateFormatException(year + month + day, e);
        }
    }

    public static String getDayFromDate(LocalDate date) {
        int day = date.getDayOfMonth();
        return String.valueOf(day);
    }

    public static String getMonthFromDate(LocalDate date) {
        int month = date.getMonthValue();
        return String.valueOf(month);
    }

    public static String getYearFromDate(LocalDate date) {
        int year = date.getYear();
        return String.valueOf(year);
    }
}
