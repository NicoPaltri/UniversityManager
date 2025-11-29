package universitymanager;

import dbmanager.DBExamInterrogation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.commons.math3.stat.StatUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class UniversityManager {
    DBExamInterrogation dbInterrogator;

    public UniversityManager() {
        this.dbInterrogator = new DBExamInterrogation();
    }

    public ObservableList<Exam> getObservableListFromDB() {
        List<Exam> exams = dbInterrogator.getAllExams();
        exams.sort(Comparator.comparing(Exam::getDate));

        moreReadableDate(exams);

        System.out.println("La lista che ho ottenuto dal DB è: " + exams.toString());

        return FXCollections.observableArrayList(exams);
    }

    private void moreReadableDate(List<Exam> exams) {
        DateTimeFormatter inputFormatter = DateTimeFormatter.BASIC_ISO_DATE; // yyyyMMdd
        DateTimeFormatter outputFormatter = DateTimeFormatter.ISO_LOCAL_DATE; // yyyy-MM-dd

        for (Exam exam : exams) {
            String newDate = LocalDate.parse(exam.getDate(), inputFormatter).format(outputFormatter);
            exam.setDate(newDate);
        }

    }

    public static int getTotalExamsWeight(List<Exam> exams) {
        return exams.stream()
                .mapToInt(Exam::getWeight)
                .sum();
    }

    public static double getArithmeticMeanFromExamList(List<Exam> exams) {
        return exams.stream()
                .mapToDouble(Exam::getGrade)
                .average()
                .orElse(0.0);
    }

    public static double getWeightedMeanFromExamList(List<Exam> exams) {
        double weighted = 0;
        double weights = 0;

        for (Exam e : exams) {
            weighted += e.getGrade() * e.getWeight();
            weights += e.getWeight();
        }

        return weights == 0 ? 0 : weighted / weights;
    }

    public static double getMedianFromExamList(List<Exam> exams) {
        if (exams.isEmpty()) {
            return 0;
        }

        List<Exam> gradeOrderedExams = new java.util.ArrayList<>(List.copyOf(exams));
        gradeOrderedExams.sort(Comparator.comparing(Exam::getGrade));

        if (exams.size() % 2 == 0) {
            double minorMedian = gradeOrderedExams.get(gradeOrderedExams.size() / 2 - 1).getGrade();
            double majorMedian = gradeOrderedExams.get(gradeOrderedExams.size() / 2).getGrade();

            return (majorMedian + minorMedian) / 2;
        } else {
            return exams.get(exams.size() / 2).getGrade();
        }
    }

    public static int getModeFromExamList(List<Exam> exams) {
        if (exams.isEmpty()) {
            return 0;
        }

        if (exams.size() == 1) {
            return exams.getFirst().getGrade();
        }

        double[] gradesArray = exams.stream().mapToDouble(Exam::getGrade).toArray();
        double[] candidatesModes = StatUtils.mode(gradesArray);

        if (candidatesModes.length == 1) {
            return (int) candidatesModes[0];
        }

        double mean = getArithmeticMeanFromExamList(exams);
        double probableMode = 0;

        for (Double v : candidatesModes) {
            if (Math.abs(v - mean) < Math.abs(probableMode - mean)) {
                probableMode = v;
            }
        }

        return (int) probableMode;
    }

    public static double getStandardDeviationFromExamList(List<Exam> exams) {
        if (exams.isEmpty()) {
            return 0;
        }

        List<Double> grades = exams.stream().map(Exam::getGrade).map(Integer::doubleValue).toList();

        double mean = getArithmeticMeanFromExamList(exams);

        double calculations = grades.stream().mapToDouble(e -> Math.pow(e - mean, 2)).sum();

        return Math.sqrt(calculations / exams.size());
    }

    public static double getVarianceFromExamList(List<Exam> exams) {
        if (exams.isEmpty()) {
            return 0;
        }

        return Math.pow(getStandardDeviationFromExamList(exams), 2);
    }

    public static double getWeightedMeanOfLastFiveExamsFromList(List<Exam> exams) {
        if (exams.isEmpty()) {
            return 0;
        }

        int numberOfExams = exams.size();

        if (exams.size() > 5) {
            numberOfExams = 5;
        }

        List<Exam> lastExamsList = exams.subList(exams.size() - numberOfExams, exams.size() - 1);

        return getWeightedMeanFromExamList(lastExamsList);
    }
}
