package application.applicationcontrollers.settingsbuttonapplication;

import application.applicationutils.FXMLUtils;
import application.applicationutils.openwindowmanager.OpenWindowUtils;
import application.applicationutils.openwindowmanager.WindowRequest;
import dbmanager.settingsTable.DBSettingRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import settingsmanager.Setting;

public class SettingsController {
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
        DBSettingRepository settingsInterrogation = new DBSettingRepository();
        settings.setAll(settingsInterrogation.getAll());

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

    public void selectButtonOnAction() {
        Setting selected = settingsTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            OpenWindowUtils.errorAlert("Seleziona una riga prima di continuare.");
            return;
        }

        WindowRequest<ChosenSettingController> windowRequest = new WindowRequest<>(
                "Modifica impostazioni",
                "/stages/settingsstages/ModifyChosenSettingStage.fxml"
        );
        windowRequest.withOverrideCss("/styles/specificSettingStage.css");
        windowRequest.withOwnerPane(mainPane);
        windowRequest.withControllerInitializer((ChosenSettingController c) -> c.initSetting(selected));
        windowRequest.withOnClose(this::updateSettings);

        OpenWindowUtils utils = new OpenWindowUtils();
        utils.openNewWindow(windowRequest);
    }

}
