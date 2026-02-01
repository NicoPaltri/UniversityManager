package application.settingsbuttonapplication;

import application.FXMLUtils;
import application.OpenWindowUtils;
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

    private void updateSettings() {
        DBSettingsInterrogation settingsInterrogation = new DBSettingsInterrogation();
        settings.setAll(settingsInterrogation.getAllPersonalizedSettings());

        FXMLUtils.clearTableSelection(settingsTable);
    }

    private void setUpSettingsTable() {
        colName.prefWidthProperty().bind(settingsTable.widthProperty().multiply(0.65));
        colValue.prefWidthProperty().bind(settingsTable.widthProperty().multiply(0.35));

        settingsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
    }

    private void linkSettingsTableAndSettingsList() {
        settingsTable.setItems(settings);

        colName.setCellValueFactory(cell -> cell.getValue().getNameProperty());
        colValue.setCellValueFactory(cell -> cell.getValue().getValueProperty());

        //impedisce anche il riordino tramite sort programmatico
        settingsTable.setSortPolicy(tv -> false);
    }

    public void selectButtonOnAction(ActionEvent actionEvent) {
        Setting selected = settingsTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            OpenWindowUtils.errorAlert("Seleziona una riga prima di continuare.");
            return;
        }

        String selectedKey = selected.getName();

        OpenWindowUtils utils = new OpenWindowUtils();
        utils.openNewWindow(
                "Modifica impostazioni",
                "/stages/settingsstages/ModifyChosenSettingStage.fxml",
                "/styles/secificSettingStage.css",
                mainPane,
                (ChosenSettingController c) -> c.initSetting(selectedKey),
                () -> {

                    //Questo viene eseguito SOLO dopo la chiusura della finestra!
                    updateSettings();

                    System.out.println("La finestra è stata chiusa. Aggiorno i dati…");
                });

    }

}
