package customexceptions.accessdatasexception;

import customexceptions.ApplicationException;

public class ConstraintViolationException extends ApplicationException {
    public static String buildMessage(String name) {
        return "Violazione di un vincolo durante l'inserimento di " + name;
    }

    public ConstraintViolationException(String name) {
        super(buildMessage(name));
    }

    public ConstraintViolationException(String name, Throwable e) {
        super(buildMessage(name), e);
    }
}
