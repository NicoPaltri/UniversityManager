package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import universitymanager.Exam;
import universitymanager.UniversityManager;

public class FXMLUtils {
    public static ObservableList<Exam> getEmptyObservableList() {
        return FXCollections.observableArrayList();
    }

    public static void setUpStandardExamTable(TableView<Exam> examTable,
                                              TableColumn<Exam, String> colName,
                                              TableColumn<Exam, Number> colWeight,
                                              TableColumn<Exam, Number> colGrade,
                                              TableColumn<Exam, String> colDate) {

        colName.prefWidthProperty().bind(examTable.widthProperty().multiply(0.50));
        colWeight.prefWidthProperty().bind(examTable.widthProperty().multiply(0.10));
        colGrade.prefWidthProperty().bind(examTable.widthProperty().multiply(0.10));
        colDate.prefWidthProperty().bind(examTable.widthProperty().multiply(0.30));

        examTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    public static void linkTableViewAndObservableList(ObservableList<Exam> exams,
                                                      TableView<Exam> examTable,
                                                      TableColumn<Exam, String> colName,
                                                      TableColumn<Exam, Number> colWeight,
                                                      TableColumn<Exam, Number> colGrade,
                                                      TableColumn<Exam, String> colDate) {

        colName.setCellValueFactory(c -> c.getValue().nameProperty());
        colWeight.setCellValueFactory(c -> c.getValue().weightProperty());
        colGrade.setCellValueFactory(c -> c.getValue().gradeProperty());
        colDate.setCellValueFactory(c -> c.getValue().dateProperty());

        examTable.setItems(exams);
    }

    public static void commonUpdateDatas(ObservableList<Exam> exams) {
        updateList(exams);
    }

    private static void updateList(ObservableList<Exam> exams) {
        UniversityManager universityManager = new UniversityManager();

        exams.setAll(universityManager.getObservableListFromDB());
    }
}
