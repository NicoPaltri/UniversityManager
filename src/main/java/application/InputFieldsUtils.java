package application;

import customexceptions.encapsulateexception.EncapsulateNumberFormatException;
import javafx.scene.control.TextField;

public class InputFieldsUtils {
    public InputFieldsUtils() {
    }

    public static int getIntParameterFromInputField(TextField textField, String parameter) {
        try {
            return Integer.parseInt(textField.getText());
        } catch (NumberFormatException e) {
            throw new EncapsulateNumberFormatException("weight", e);
        }
    }

    public static String getStringParameterFromInputField(TextField textField){
        return textField.getText().trim();
    }
}
