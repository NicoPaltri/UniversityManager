package settingsmanager;

import customexceptions.settingsexcpetions.InvalidCFUValueException;
import customexceptions.settingsexcpetions.UnknownSettingException;

public enum ApplicationSettings {
    TOTAL_CFU("totalCFU",
            180,
            newValue -> {
                if (newValue <= 0) throw new InvalidCFUValueException();
            });

    private final String name;
    private final int defaultValue;
    private final SettingValidator validator;

    ApplicationSettings(String name, int defaultValue, SettingValidator validator) {
        this.name = name;
        this.defaultValue = defaultValue;
        this.validator = validator;
    }

    public String getName() {
        return name;
    }

    public int getDefaultValue() {
        return defaultValue;
    }

    public void validate(int newValue) {
        this.validator.validate(newValue);
    }

    public static ApplicationSettings fromName(String name) {
        for (ApplicationSettings s : values()) {
            if (s.name.equals(name)) {
                return s;
            }
        }
        throw new UnknownSettingException(name);
    }


}

