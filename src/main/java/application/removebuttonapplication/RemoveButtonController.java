package application.removebuttonapplication;

import application.FXMLUtils;
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
        // Tabella responsiva
        FXMLUtils.setUpStandardExamTable(examTable, colName, colWeight, colGrade, colDate);

        // Collego tabella e lista
        FXMLUtils.linkTableViewAndObservableList(exams,
                examTable,colName,colWeight,colGrade,colDate);

        // Disable del bottone quando nulla è selezionato
        deleteButton.disableProperty().bind(
                examTable.getSelectionModel().selectedItemProperty().isNull()
        );

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

    private void updateDatas() {
        FXMLUtils.commonUpdateDatas(exams,examTable);

        //CONTROLLER SPECIFIC UPDATES
    }
}
