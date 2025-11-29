package application.removebuttonapplication;

import dbmanager.DBManageDB;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import universitymanager.Exam;
import universitymanager.UniversityManager;

public class RemoveButtonController {

    public TableView<Exam> examTable;
    public TableColumn<Exam, String> colName;
    public TableColumn<Exam, Number> colWeight;
    public TableColumn<Exam, Number> colGrade;
    public TableColumn<Exam, String> colDate;

    public Button deleteButton;


    private final ObservableList<Exam> exams = FXCollections.observableArrayList();

    public void initialize() {
        // Tabella responsiva (best practice)
        colName.prefWidthProperty().bind(examTable.widthProperty().multiply(0.50));
        colWeight.prefWidthProperty().bind(examTable.widthProperty().multiply(0.10));
        colGrade.prefWidthProperty().bind(examTable.widthProperty().multiply(0.10));
        colDate.prefWidthProperty().bind(examTable.widthProperty().multiply(0.30));

        examTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Disable del bottone quando nulla è selezionato
        deleteButton.disableProperty().bind(
                examTable.getSelectionModel().selectedItemProperty().isNull()
        );

        linkTableViewAndList(exams);
        updateDatas();
    }

    public void removeButtonOnAction(ActionEvent actionEvent) {

        Exam exam = examTable.getSelectionModel().getSelectedItem();

        if (exam == null) {
            System.out.println("Nessun esame selezionato.");
            return;
        }

        DBManageDB.deleteExamByName(exam.getName());

        updateDatas();
    }


    private void linkTableViewAndList(ObservableList<Exam> exams) {
        colName.setCellValueFactory(c -> c.getValue().nameProperty());
        colWeight.setCellValueFactory(c -> c.getValue().weightProperty());
        colGrade.setCellValueFactory(c -> c.getValue().gradeProperty());
        colDate.setCellValueFactory(c -> c.getValue().dateProperty());

        examTable.setItems(exams);
    }

    private void updateDatas() {
        UniversityManager universityManager = new UniversityManager();

        exams.setAll(universityManager.getObservableListFromDB());

        Platform.runLater(() -> examTable.getSelectionModel().clearSelection());
    }
}
