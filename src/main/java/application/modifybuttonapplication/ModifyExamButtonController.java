package application.modifybuttonapplication;

import application.FXMLUtils;
import application.OpenWindowUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import universitymanager.examtypes.Exam;

public class ModifyExamButtonController {

    public BorderPane mainPane;

    public TableView<Exam> examTable;
    public TableColumn<Exam, String> colName;
    public TableColumn<Exam, Number> colWeight;
    public TableColumn<Exam, String> colGrade;
    public TableColumn<Exam, String> colDate;

    public Button selectButton;


    private final ObservableList<Exam> exams = FXCollections.observableArrayList();

    public void initialize() {
        // Tabella responsiva
        FXMLUtils.setUpStandardExamTable(examTable, colName, colWeight, colGrade, colDate);

        // Collego tabella e lista
        FXMLUtils.linkTableViewAndObservableList(exams,
                examTable, colName, colWeight, colGrade, colDate);

        // Disable del bottone quando nulla è selezionato
        selectButton.disableProperty().bind(
                examTable.getSelectionModel().selectedItemProperty().isNull()
        );

        updateDatas();
    }

    private void updateDatas() {
        FXMLUtils.commonUpdateDatas(exams, examTable);

        //CONTROLLER SPECIFIC UPDATES
    }

    public void selectButtonOnAction(ActionEvent actionEvent) {
        Exam selectedExam = examTable.getSelectionModel().getSelectedItem();
        if (selectedExam == null) {
            OpenWindowUtils.errorAlert("Seleziona una riga prima di continuare.");
            return;
        }

        OpenWindowUtils utils = new OpenWindowUtils();
        utils.openNewWindow(
                "Modifica esame",
                "/stages/modifystages/ModifyChosenExamStage.fxml",
                "/styles/modify_exam.css",
                mainPane,
                (ChosenExamController c) -> c.initExam(selectedExam), () -> {

                    //Questo viene eseguito SOLO dopo la chiusura della finestra!
                    updateDatas();

                    System.out.println("La finestra è stata chiusa. Aggiorno i dati…");
                }
        );
    }

}
