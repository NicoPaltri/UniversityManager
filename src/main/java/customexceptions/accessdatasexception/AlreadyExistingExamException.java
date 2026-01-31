package customexceptions.accessdatasexception;

import customexceptions.ApplicationException;

public class AlreadyExistingExamException extends ApplicationException {

    private static String buildMessage(String name) {
        return "Violazione di un vincolo durante la l'inserimento di: " + name;
    }

    public AlreadyExistingExamException(String name) {
        super(buildMessage(name));
    }

    public AlreadyExistingExamException(String name, Throwable cause) {
        super(buildMessage(name), cause);
    }

}
