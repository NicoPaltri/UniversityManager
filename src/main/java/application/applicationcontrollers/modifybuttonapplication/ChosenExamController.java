package application.applicationcontrollers.modifybuttonapplication;

import application.applicationutils.ExamUtils;
import application.applicationutils.InputFieldsUtils;
import application.applicationutils.openwindowmanager.OpenWindowUtils;
import customexceptions.ApplicationException;
import dbmanager.examsTable.DBExamRepository;
import examsmanager.examfactories.ExamCreationData;
import examsmanager.examtypes.Idoneita;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import examsmanager.examfactories.GradedExamFactory;
import examsmanager.examfactories.IdoneitaFactory;
import examsmanager.examtypes.Exam;
import examsmanager.examtypes.GradedExam;

import java.time.LocalDate;
import java.util.Optional;

public class ChosenExamController {

    public TextField nameInputField;
    public TextField weightInputField;
    public TextField gradeInputField;

    public TextField dayInputField;
    public TextField monthInputField;
    public TextField yearInputField;

    public CheckBox idoneitaCheckBox;

    public Button modifyButton;


    private Exam selectedExam;

    public void initialize() {
        //initExams viene chiamato solo dopo initialize, quindi
        //in initialize non si può usare selectedExam perchè è sempre null

        //mostro/non mostro gradTextField
        gradeInputField.visibleProperty()
                .bind(idoneitaCheckBox.selectedProperty().not());

        gradeInputField.managedProperty()
                .bind(gradeInputField.visibleProperty());
    }

    public void initExam(Exam exam) {
        this.selectedExam = exam;
        setLabels();
    }

    private void setLabels() {
        nameInputField.setText(selectedExam.getName());
        weightInputField.setText(String.valueOf(selectedExam.getWeight()));

        Optional<Integer> grade = selectedExam.getGrade();
        if (grade.isEmpty()) {
            idoneitaCheckBox.setSelected(true);
            gradeInputField.setText("0");
        } else {
            idoneitaCheckBox.setSelected(false);
            gradeInputField.setText(String.valueOf(grade.get()));
        }

        dayInputField.setText(ExamUtils.getDayFromDate(selectedExam.getDate()));
        monthInputField.setText(ExamUtils.getMonthFromDate(selectedExam.getDate()));
        yearInputField.setText(ExamUtils.getYearFromDate(selectedExam.getDate()));
    }

    public void modifyButtonOnAction(ActionEvent actionEvent) {
        DBExamRepository dbManager = new DBExamRepository();

        try {
            Exam modifiedExam = getExamFromFields();

            dbManager.deleteByName(selectedExam.getName());
            dbManager.insert(modifiedExam);

            Stage thisStage = (Stage) modifyButton.getScene().getWindow();
            thisStage.close();

        } catch (ApplicationException e) {
            OpenWindowUtils.errorAlert(e.getMessage());
        }
    }

    private Exam getExamFromFields() {
        GradedExamFactory gradedExamFactory = new GradedExamFactory();
        IdoneitaFactory idoneitaFactory = new IdoneitaFactory();

        String name = nameInputField.getText().trim();
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

        return exam;
    }

}
