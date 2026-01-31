package customexceptions.encapsulateexception;

import customexceptions.ApplicationException;

public class EncapsulateNumberFormatException extends ApplicationException {

    private static String buildMessage(String parameter) {
        return "Errore durante il parsing di " + parameter;
    }

    public EncapsulateNumberFormatException(String parameter) {
        super(buildMessage(parameter));
    }

    public EncapsulateNumberFormatException(String parameter, Throwable e) {
        super(buildMessage(parameter), e);
    }

}
