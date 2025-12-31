package application.settingsbuttonapplication;

import application.FXMLUtils;
import dbmanager.settingsTable.DBSettingsInterrogation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import settingsmanager.Setting;

public class SettingsButtonController {
    public BorderPane mainPane;

    public TableView<Setting> settingsTable;
    public TableColumn<Setting, String> colName;
    public TableColumn<Setting, Number> colValue;

    public Button selectButton;


    private final ObservableList<Setting> settings = FXCollections.observableArrayList();

    public void initialize() {
        setUpSettingsTable();

        linkSettingsTableAndSettingsList();

        selectButton.disableProperty().bind(
                settingsTable.getSelectionModel().selectedItemProperty().isNull()
        );

        updateSettings();
    }

    public void modifyButtonOnAction(ActionEvent actionEvent) {
        FXMLUtils utils = new FXMLUtils();

        utils.openNewWindow("Modifica impostazioni", "/stages/SettingsStage.fxml",
                "/styles/settings_stage.css", mainPane,
                () -> {

                    //Questo viene eseguito SOLO dopo la chiusura della finestra!
                    updateSettings();

                    System.out.println("La finestra è stata chiusa. Aggiorno i dati…");
                });
    }

    private void setUpSettingsTable() {
        colName.prefWidthProperty().bind(settingsTable.widthProperty().multiply(0.65));
        colValue.prefWidthProperty().bind(settingsTable.widthProperty().multiply(0.35));

        settingsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
    }

    private void updateSettings() {
        DBSettingsInterrogation settingsInterrogation = new DBSettingsInterrogation();
        settings.setAll(settingsInterrogation.getAllSettings());

        FXMLUtils.clearTableSelection(settingsTable);
    }

    private void linkSettingsTableAndSettingsList() {
        colName.setCellValueFactory(cell -> cell.getValue().nameProperty());
        colValue.setCellValueFactory(cell -> cell.getValue().valueProperty());

        //impedisce anche il riordino tramite sort programmatico
        settingsTable.setSortPolicy(tv -> false);
    }
}
