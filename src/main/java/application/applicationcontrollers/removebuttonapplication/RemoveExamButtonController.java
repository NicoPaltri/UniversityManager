package application.applicationcontrollers.removebuttonapplication;

import application.applicationutils.FXMLUtils;
import dbmanager.examsTable.DBExamRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import examsmanager.examtypes.Exam;

public class RemoveExamButtonController {

    public TableView<Exam> examTable;
    public TableColumn<Exam, String> colName;
    public TableColumn<Exam, Number> colWeight;
    public TableColumn<Exam, String> colGrade;
    public TableColumn<Exam, String> colDate;

    public Button deleteButton;


    private final ObservableList<Exam> exams = FXCollections.observableArrayList();

    public void initialize() {
        // Tabella responsiva
        FXMLUtils.setUpStandardExamTable(examTable, colName, colWeight, colGrade, colDate);

        // Collego tabella e lista
        FXMLUtils.linkTableViewAndObservableList(exams,
                examTable, colName, colWeight, colGrade, colDate);

        // Disable del bottone quando nulla è selezionato
        deleteButton.disableProperty().bind(
                examTable.getSelectionModel().selectedItemProperty().isNull()
        );

        updateDatas();
    }

    public void removeButtonOnAction() {
        DBExamRepository dbManager = new DBExamRepository();

        Exam exam = examTable.getSelectionModel().getSelectedItem();

        if (exam == null) {
            return;
        }

        dbManager.deleteByName(exam.getName());

        updateDatas();
    }

    private void updateDatas() {
        FXMLUtils.commonUpdateDatas(exams, examTable);

        //CONTROLLER SPECIFIC UPDATES
    }
}
