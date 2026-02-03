package settingsmanager;

import customexceptions.ApplicationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ApplicationSettingsTest {
    ApplicationSettings totalCfuSetting = ApplicationSettings.fromName(ApplicationSettings.TOTAL_CFU.getName());


    // TESTS FOR validate

    // tests for TotalCFU

    @Test
    void cfu_tooLow_throwsException() {
        assertThrows(ApplicationException.class, () -> {
            totalCfuSetting.validate(0);
        });
    }

    @Test
    void cfu_limitLow_doesNotThrow() {
        assertDoesNotThrow(() -> {
            totalCfuSetting.validate(1);
        });
    }


    // TESTS FOR fromName

    @Test
    void applicationSetting_exists_doesNotThrow() {
        assertDoesNotThrow(() -> {
            ApplicationSettings.fromName(ApplicationSettings.TOTAL_CFU.getName());
        });
    }

    @Test
    void applicationSetting_notExists_throwsException() {
        assertThrows(ApplicationException.class, () -> {
            ApplicationSettings.fromName("NoWayAnExceptionLikeThisExistsInMySoftware");
        });
    }

}