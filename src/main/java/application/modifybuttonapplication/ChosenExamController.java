package application.modifybuttonapplication;

import application.FXMLUtils;
import customexceptions.ApplicationException;
import dbmanager.examsTable.DBManageExams;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import universitymanager.examtypes.Exam;
import universitymanager.examfactories.ExamFactory;

public class ChosenExamController {

    public TextField nameInputField;
    public TextField weightInputField;
    public TextField gradeInputField;

    public TextField dayInputField;
    public TextField monthInputField;
    public TextField yearInputField;

    public Button modifyButton;


    private Exam selectedExam;

    public void initialize() {
        //initExams viene chiamato solo dopo initialize, quindi
        //in initialize non si può usare selectedExam perchè è sempre null
    }

    public void initExam(Exam exam) {
        this.selectedExam = exam;
        setLabels();
    }

    private void setLabels() {
        nameInputField.setText(selectedExam.getName());
        weightInputField.setText(String.valueOf(selectedExam.getWeight()));
        gradeInputField.setText(String.valueOf(selectedExam.getGrade()));

        //REMEMBER: aaaa-mm-dd
        dayInputField.setText(selectedExam.getDate().substring(8, 10));
        monthInputField.setText(selectedExam.getDate().substring(5, 7));
        yearInputField.setText(selectedExam.getDate().substring(0, 4));
    }

    public void modifyButtonOnAction(ActionEvent actionEvent) {
        try {
            Exam modifiedExam = getExamFromFields();

            DBManageExams.deleteExamByName(selectedExam.getName());
            DBManageExams.insertExam(modifiedExam);

            Stage thisStage = (Stage) modifyButton.getScene().getWindow();
            thisStage.close();

        } catch (NumberFormatException e) {
            FXMLUtils.errorAlert("Errore nel format di grade/weight.");
        } catch (ApplicationException e) {
            FXMLUtils.errorAlert(e.getMessage());
        }
    }

    private Exam getExamFromFields() {
        String name = nameInputField.getText().trim();
        int weight = Integer.parseInt(weightInputField.getText());
        int grade = Integer.parseInt(gradeInputField.getText());

        String day = dayInputField.getText().trim();
        day = FXMLUtils.makeThisTwoDigits(day);

        String month = monthInputField.getText().trim();
        month = FXMLUtils.makeThisTwoDigits(month);

        String year = yearInputField.getText().trim();

        String completeDate = year + month + day;

        ExamFactory examFactory = new ExamFactory();
        return examFactory.createExam(name, weight, grade, completeDate);
    }

}
