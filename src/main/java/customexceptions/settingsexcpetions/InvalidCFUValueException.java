package customexceptions.settingsexcpetions;

import customexceptions.ApplicationException;

public class InvalidCFUValueException extends ApplicationException {

    private static String buildMessage() {
        return "Il numero totale di CFU non può essere minore o uguale a 0";
    }

    public InvalidCFUValueException() {
        super(buildMessage());
    }

    public InvalidCFUValueException(Throwable e) {
        super(buildMessage(), e);
    }

}
