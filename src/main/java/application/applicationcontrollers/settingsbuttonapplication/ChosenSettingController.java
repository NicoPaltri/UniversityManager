package application.applicationcontrollers.settingsbuttonapplication;

import application.applicationutils.InputFieldsUtils;
import application.applicationutils.openwindowmanager.OpenWindowUtils;
import customexceptions.ApplicationException;
import dbmanager.settingsTable.DBSettingsInterrogation;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import settingsmanager.ApplicationSettings;
import settingsmanager.Setting;

public class ChosenSettingController {

    public Label nameLabel;
    public TextField valueInputField;

    private Setting chosenSetting;


    public void initialize() {
    }

    public void initSetting(Setting chosenSetting) {
        this.chosenSetting = chosenSetting;
        nameLabel.setText(chosenSetting.getName());
    }

    public void modifyButtonOnAction(ActionEvent actionEvent) {
        try {
            int newValue = InputFieldsUtils.getIntParameterFromInputField(valueInputField, "newValue");

            ApplicationSettings applicationSettings = ApplicationSettings.fromName(chosenSetting.getName());
            applicationSettings.validate(newValue);

            DBSettingsInterrogation dbSettingsInterrogation = new DBSettingsInterrogation();
            dbSettingsInterrogation.changeSetting(chosenSetting, newValue);

            Stage thisStage = (Stage) nameLabel.getScene().getWindow();
            thisStage.close();

        } catch (ApplicationException e) {
            OpenWindowUtils.errorAlert(e.getMessage());
        }
    }


}
