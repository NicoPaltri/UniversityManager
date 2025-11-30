package application.addbuttonapplication;

import application.FXMLUtils;
import customexceptions.dateexception.FutureDateException;
import customexceptions.dateexception.InvalidDateFormatException;
import customexceptions.examformatexception.GradeFormatException;
import customexceptions.examformatexception.WeightFormatException;
import dbmanager.DBManageDB;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
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
            String month = monthInputField.getText().trim();
            String year = yearInputField.getText().trim();
            String completeDate = yearInputField.getText().trim() +
                    monthInputField.getText().trim() +
                    dayInputField.getText().trim();

            Exam exam = examFactory.createExam(name, weight, grade, completeDate);

            setEveryFieldToBlank();

            DBManageDB.insertExam(exam);

        } catch (NumberFormatException e) {
            System.out.println("Errore nel format di grade/weight.");
        } catch (WeightFormatException |
                 GradeFormatException |
                 FutureDateException |
                 InvalidDateFormatException e) {
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
