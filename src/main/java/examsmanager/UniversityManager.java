package examsmanager;

import dbmanager.examsTable.DBExamRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import examsmanager.examtypes.Exam;
import java.util.*;

public class UniversityManager {
    DBExamRepository dbInterrogator;

    public UniversityManager() {
        this.dbInterrogator = new DBExamRepository();
    }


    public ObservableList<Exam> getExamOrderedObservableListFromDB() {
        List<Exam> exams = dbInterrogator.getAll();
        exams.sort(Comparator.comparing(Exam::getDate));

        System.out.println("La lista che ho ottenuto dal DB è: " + exams.toString());

        return FXCollections.observableArrayList(exams);
    }

}
