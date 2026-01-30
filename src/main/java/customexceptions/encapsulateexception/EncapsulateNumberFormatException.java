package customexceptions.encapsulateexception;

import customexceptions.ApplicationException;

public class EncapsulateNumberFormatException extends ApplicationException {
    public EncapsulateNumberFormatException(String parameter) {
        super("Errore durante il parsing di " + parameter);
    }

    public EncapsulateNumberFormatException(String parameter, Throwable e) {
        super("Errore durante il parsing di " + parameter + " [" + e + "]");
    }
}
