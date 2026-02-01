package dbmanager.settingsTable;

import settingsmanager.Setting;

import java.util.Arrays;
import java.util.List;

public enum ApplicationSettings {
    TOTAL_CFU("totalCFU", 180);

    private final String settingName;
    private final int settingDefaultValue;

    ApplicationSettings(String settingName, int settingDefaultValue) {
        this.settingName = settingName;
        this.settingDefaultValue = settingDefaultValue;
    }

    public String getSettingName() {
        return settingName;
    }

    public int getSettingDefaultValue() {
        return settingDefaultValue;
    }

    public static List<Setting> getAllDefaultSettings() {
        return Arrays.stream(ApplicationSettings.values())
                .map(s -> new Setting(
                        s.getSettingName(),
                        s.getSettingDefaultValue()
                ))
                .toList();
    }

}

