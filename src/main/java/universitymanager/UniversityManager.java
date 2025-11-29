package universitymanager;

import dbmanager.DBExamInterrogation;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Callable;

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
}
