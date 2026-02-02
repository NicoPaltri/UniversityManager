package application.applicationutils;

import customexceptions.dateexception.InvalidDateFormatException;
import universitymanager.examtypes.Exam;
import universitymanager.examtypes.GradedExam;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

public class ExamUtils {
    public ExamUtils() {
    }

    public static List<GradedExam> filterGradedExamsFromExamList(List<Exam> exams) {
        return exams.stream()
                .filter(e -> e instanceof GradedExam)
                .map(e -> (GradedExam) e)
                .collect(Collectors.toList());
    }

    public static LocalDate buildLocalDate(String year, String month, String day) {
        final String raw = year + "-" + month + "-" + day;

        try {
            int y = Integer.parseInt(year.trim());
            int m = Integer.parseInt(month.trim());
            int d = Integer.parseInt(day.trim());

            return LocalDate.of(y, m, d);
        } catch (NumberFormatException | DateTimeException e) {
            throw new InvalidDateFormatException(raw, e);
        }
    }

    public static LocalDate parseIsoDate(String isoDate) {
        if (isoDate == null) {
            throw new InvalidDateFormatException("null");
        }

        String date = isoDate.trim();
        if (date.isEmpty()) {
            throw new InvalidDateFormatException("empty string");
        }

        try {
            return LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (DateTimeParseException e) {
            throw new InvalidDateFormatException(date, e);
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
