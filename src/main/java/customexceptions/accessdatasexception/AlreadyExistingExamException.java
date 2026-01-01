package customexceptions.accessdatasexception;

import customexceptions.ApplicationException;

public class AlreadyExistingExamException extends ApplicationException {
    public AlreadyExistingExamException(String name) {
        super("L'esame inserito è già presente nel database " + name);
    }

    public AlreadyExistingExamException(String name, Throwable e) {
        super("L'esame inserito è già presente nel database " + name + "; " + e);
    }

}
