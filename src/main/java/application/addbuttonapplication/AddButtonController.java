package application.addbuttonapplication;

import customexceptions.dateexception.InvalidDateFormatException;
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
            String name = nameInputField.getText();
            int weight = Integer.parseInt(weightInputField.getText());
            int grade = Integer.parseInt(gradeInputField.getText());

            String day = dayInputField.getText();
            String month = monthInputField.getText();
            String year = yearInputField.getText();
            String completeDate = yearInputField.getText() +
                                monthInputField.getText() +
                                dayInputField.getText();

            setEveryFieldToBlank();

            Exam exam = examFactory.createExam(name, weight, grade, completeDate);

            DBManageDB.insertExam(exam);

        } catch (NumberFormatException e) {
            System.out.println("Errore nel format di grade/weight.");
        }
    }

    private void setEveryFieldToBlank(){
        nameInputField.setText("");
        weightInputField.setText("");
        gradeInputField.setText("");

        dayInputField.setText("");
        monthInputField.setText("");
        yearInputField.setText("");
    }
}
