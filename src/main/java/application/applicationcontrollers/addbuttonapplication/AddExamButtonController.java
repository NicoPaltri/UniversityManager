package application.applicationcontrollers.addbuttonapplication;

import application.applicationutils.ExamUtils;
import application.applicationutils.InputFieldsUtils;
import application.applicationutils.openwindowmanager.OpenWindowUtils;
import customexceptions.ApplicationException;
import dbmanager.examsTable.DBManageExams;
import examsmanager.examfactories.ExamCreationData;
import javafx.event.ActionEvent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import examsmanager.examfactories.GradedExamFactory;
import examsmanager.examfactories.IdoneitaFactory;
import examsmanager.examtypes.Exam;

import java.time.LocalDate;

public class AddExamButtonController {

    public TextField nameInputField;
    public TextField weightInputField;
    public TextField gradeInputField;

    public TextField dayInputField;
    public TextField monthInputField;
    public TextField yearInputField;

    public CheckBox idoneitaCheckBox;


    public void initialize() {
        gradeInputField.visibleProperty()
                .bind(idoneitaCheckBox.selectedProperty().not());

        gradeInputField.managedProperty()
                .bind(gradeInputField.visibleProperty());
    }

    public void confirmButtonOnAction(ActionEvent actionEvent) {
        try {
            GradedExamFactory gradedExamFactory = new GradedExamFactory();
            IdoneitaFactory idoneitaFactory = new IdoneitaFactory();

            String name = InputFieldsUtils.getStringParameterFromInputField(nameInputField);
            int weight = InputFieldsUtils.getIntParameterFromInputField(weightInputField, "weight");

            String day = InputFieldsUtils.getStringParameterFromInputField(dayInputField);
            String month = InputFieldsUtils.getStringParameterFromInputField(monthInputField);
            String year = InputFieldsUtils.getStringParameterFromInputField(yearInputField);

            LocalDate completeDate = ExamUtils.buildLocalDate(year, month, day);

            Exam exam;
            ExamCreationData data = new ExamCreationData(name, weight, completeDate);

            if (idoneitaCheckBox.isSelected()) {
                exam = idoneitaFactory.createExam(data);
            } else {
                int grade = InputFieldsUtils.getIntParameterFromInputField(gradeInputField, "grade");
                data.withGrade(grade);

                exam = gradedExamFactory.createExam(data);
            }

            DBManageExams.insertExam(exam);

            setEveryFieldToBlank();

        } catch (ApplicationException e) {
            OpenWindowUtils.errorAlert(e.getMessage());
        }
    }

    private void setEveryFieldToBlank() {
        nameInputField.setText("");
        weightInputField.setText("");
        gradeInputField.setText("");

        dayInputField.setText("");
        monthInputField.setText("");
        yearInputField.setText("");

        idoneitaCheckBox.setSelected(false);
    }

}
