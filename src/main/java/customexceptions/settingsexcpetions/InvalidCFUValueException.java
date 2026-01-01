package customexceptions.settingsexcpetions;

import customexceptions.ApplicationException;

public class InvalidCFUValueException extends ApplicationException {
    public InvalidCFUValueException() {
        super("Il numero totale di CFU non può essere minore o uguale a 0");
    }
}
