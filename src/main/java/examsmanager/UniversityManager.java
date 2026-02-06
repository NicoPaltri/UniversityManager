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

        return FXCollections.observableArrayList(exams);
    }

}
