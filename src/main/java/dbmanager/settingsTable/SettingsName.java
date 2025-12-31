package dbmanager.settingsTable;

public enum SettingsName {
    TOTAL_CFU("totalCFU");

    private final String settingName;

    SettingsName(String settingName) {
        this.settingName = settingName;
    }

    public String getSettingName() {
        return settingName;
    }
}

