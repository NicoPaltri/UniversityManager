package application;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Region;
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

    public static void commonUpdateDatas(ObservableList<Exam> exams, TableView<Exam> examTable) {
        updateList(exams);

        clearTableSelection(examTable);
    }

    private static void updateList(ObservableList<Exam> exams) {
        UniversityManager universityManager = new UniversityManager();

        exams.setAll(universityManager.getObservableListFromDB());
    }

    public static void clearTableSelection(TableView<Exam> examTable) {
        Platform.runLater(() -> examTable.getSelectionModel().clearSelection());
    }

    public static void errorAlert(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Errore di input");
        alert.setHeaderText("Operazione non valida");
        alert.setContentText(message);

        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.showAndWait();

    }
}
