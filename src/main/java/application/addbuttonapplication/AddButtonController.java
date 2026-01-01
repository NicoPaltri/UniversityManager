package application.addbuttonapplication;

import application.FXMLUtils;
import customexceptions.ApplicationException;
import dbmanager.examsTable.DBManageExams;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import universitymanager.Exam;
import universitymanager.ExamFactory;

public class AddButtonController {

    public TextField nameInputField;
    public TextField weightInputField;
    public TextField gradeInputField;

    public TextField dayInputField;
    public TextField monthInputField;
    public TextField yearInputField;


    ExamFactory examFactory = new ExamFactory();

    public void confirmButtonOnAction(ActionEvent actionEvent) {
        try {
            String name = nameInputField.getText().trim();
            int weight = Integer.parseInt(weightInputField.getText());
            int grade = Integer.parseInt(gradeInputField.getText());

            String day = dayInputField.getText().trim();
            day = FXMLUtils.makeThisTwoDigits(day);

            String month = monthInputField.getText().trim();
            month = FXMLUtils.makeThisTwoDigits(month);

            String year = yearInputField.getText().trim();

            String completeDate = year + month + day;

            Exam exam = examFactory.createExam(name, weight, grade, completeDate);

            DBManageExams.insertExam(exam);

            setEveryFieldToBlank();

        } catch (NumberFormatException e) {
            FXMLUtils.errorAlert("Errore nel format di grade/weight.");
        } catch (ApplicationException e) {
            FXMLUtils.errorAlert(e.getMessage());
        }
    }

    private void setEveryFieldToBlank() {
        nameInputField.setText("");
        weightInputField.setText("");
        gradeInputField.setText("");

        dayInputField.setText("");
        monthInputField.setText("");
        yearInputField.setText("");
    }

}
