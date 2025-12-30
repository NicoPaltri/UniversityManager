package universitymanager;

import dbmanager.examsTable.DBExamsInterrogation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class UniversityManager {
    DBExamsInterrogation dbInterrogator;

    public UniversityManager() {
        this.dbInterrogator = new DBExamsInterrogation();
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
        double num = 0;
        double weights = 0;

        for (Exam e : exams) {
            num += e.getGrade() * e.getWeight();
            weights += e.getWeight();
        }

        return weights == 0 ? 0 : num / weights;
    }

    public static double getWeightedMeanOfLastFiveExamsFromList(List<Exam> exams) {
        if (exams.isEmpty()) {
            return 0;
        }

        List<Exam> sorted = exams.stream()
                .sorted(Comparator.comparing(Exam::getDate))
                .toList();

        int from = Math.max(0, sorted.size() - 5);
        return getWeightedMeanFromExamList(sorted.subList(from, sorted.size()));
    }


    public static double getMedianFromExamList(List<Exam> exams) {
        if (exams.isEmpty()) {
            return 0;
        }

        List<Integer> gradesList = exams.stream().map(Exam::getGrade).toList();
        List<Integer> orderedGrades = new ArrayList<>(gradesList);
        orderedGrades.sort(null);

        return getMedianFromIntegerOrderedList(orderedGrades);
    }

    private static double getMedianFromIntegerOrderedList(List<Integer> numbers) {
        if (numbers.isEmpty()) {
            return 0;
        }

        if (numbers.size() == 1) {
            return numbers.getFirst();
        }

        if (numbers.size() % 2 == 0) {
            double minorMedian = numbers.get(numbers.size() / 2 - 1);
            double majorMedian = numbers.get(numbers.size() / 2);

            return (majorMedian + minorMedian) / 2;
        } else {
            return numbers.get(numbers.size() / 2);
        }
    }


    public static int getModeFromExamList(List<Exam> exams) {
        if (exams.isEmpty()) {
            return 0;
        }

        Map<Integer, Integer> mapGradeFrequency = new HashMap<>();
        for (Exam e : exams) {
            if (mapGradeFrequency.containsKey(e.getGrade())) {
                mapGradeFrequency.replace(e.getGrade(), mapGradeFrequency.get(e.getGrade()) + 1);
            } else {
                mapGradeFrequency.put(e.getGrade(), 1);
            }
        }

        int maxFrequency = mapGradeFrequency.values()
                .stream()
                .mapToInt(Integer::intValue)
                .max()
                .orElse(0);

        List<Integer> gradesWithMaxFrequency = new ArrayList<>();
        mapGradeFrequency.forEach((key, value) -> {
            if (value == maxFrequency) {
                gradesWithMaxFrequency.add(key);
            }
        });

        if (gradesWithMaxFrequency.size() == 1) {
            return gradesWithMaxFrequency.getFirst();
        }

        gradesWithMaxFrequency.sort(null);

        return (int) getMedianFromIntegerOrderedList(gradesWithMaxFrequency);
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


    public static double getInterQuartileRangeFromExamList(List<Exam> exams) {
        if (exams.size() < 4) {
            return 0;
        }

        List<Integer> sorted = exams.stream()
                .map(Exam::getGrade)
                .sorted()
                .toList();

        int n = sorted.size();
        List<Integer> lower = sorted.subList(0, n / 2);
        List<Integer> upper = sorted.subList((n + 1) / 2, n);

        double q1 = getMedianFromIntegerOrderedList(lower);
        double q3 = getMedianFromIntegerOrderedList(upper);

        return q3 - q1;
    }

}
