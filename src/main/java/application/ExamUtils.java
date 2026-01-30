package application;

import universitymanager.examtypes.Exam;
import universitymanager.examtypes.ExamTypologies;
import universitymanager.examtypes.GradedExam;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

    public static void moreReadableDate(List<Exam> exams) {
        DateTimeFormatter inputFormatter = DateTimeFormatter.BASIC_ISO_DATE; // yyyyMMdd
        DateTimeFormatter outputFormatter = DateTimeFormatter.ISO_LOCAL_DATE; // yyyy-MM-dd

        for (Exam exam : exams) {
            String newDate = LocalDate.parse(exam.getDate(), inputFormatter).format(outputFormatter);
            exam.setDate(newDate);
        }
    }
}
