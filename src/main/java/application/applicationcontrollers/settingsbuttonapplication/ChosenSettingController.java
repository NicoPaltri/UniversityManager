package application.applicationcontrollers.settingsbuttonapplication;

import application.applicationutils.InputFieldsUtils;
import application.applicationutils.openwindowmanager.OpenWindowUtils;
import customexceptions.ApplicationException;
import customexceptions.settingsexcpetions.InvalidCFUValueException;
import dbmanager.settingsTable.DBSettingsInterrogation;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import dbmanager.settingsTable.ApplicationSettings;
import javafx.stage.Stage;

public class ChosenSettingController {

    public Label nameLabel;
    public TextField valueInputField;

    private String chosenSettingName;


    public void initialize() {
    }

    public void initSetting(String chosenSettingName) {
        this.chosenSettingName = chosenSettingName;
        nameLabel.setText(chosenSettingName);
    }

    public void modifyButtonOnAction(ActionEvent actionEvent) {
        String stringValue = InputFieldsUtils.getStringParameterFromInputField(valueInputField);

        try {
            int value = Integer.parseInt(stringValue);

            //non OCP no switch allowed 'constant expression required'
            if (chosenSettingName.equals(ApplicationSettings.TOTAL_CFU.getSettingName())) {
                changeTotalCFU(value);
            }

            //se tutto è andato bene chiudo la pagina
            Stage thisStage = (Stage) nameLabel.getScene().getWindow();
            thisStage.close();

        } catch (NumberFormatException e) {
            OpenWindowUtils.errorAlert("Il valore passato deve essere un numero intero; " + e.getMessage());
        } catch (ApplicationException e) {
            OpenWindowUtils.errorAlert(e.getMessage());
        }
    }

    private void changeTotalCFU(int value) {
        if (value <= 0) {
            throw new InvalidCFUValueException();
        }

        DBSettingsInterrogation dbSettingsInterrogation = new DBSettingsInterrogation();
        dbSettingsInterrogation.changeTotalCFU(value);
    }

}
