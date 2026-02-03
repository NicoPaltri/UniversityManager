package settingsmanager;

@FunctionalInterface
public interface SettingValidator {
    void validate(int newValue);
}

