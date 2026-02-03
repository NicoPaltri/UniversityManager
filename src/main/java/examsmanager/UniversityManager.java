package examsmanager;

import dbmanager.examsTable.DBExamsInterrogation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import examsmanager.examtypes.Exam;
import java.util.*;

public class UniversityManager {
    DBExamsInterrogation dbInterrogator;

    public UniversityManager() {
        this.dbInterrogator = new DBExamsInterrogation();
    }


    public ObservableList<Exam> getExamOrderedObservableListFromDB() {
        List<Exam> exams = dbInterrogator.getAllExams();
        exams.sort(Comparator.comparing(Exam::getDate));

        System.out.println("La lista che ho ottenuto dal DB è: " + exams.toString());

        return FXCollections.observableArrayList(exams);
    }

}
