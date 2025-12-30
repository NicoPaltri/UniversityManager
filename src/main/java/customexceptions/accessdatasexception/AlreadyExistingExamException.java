package customexceptions.accessdatasexception;

public class AlreadyExistingExamException extends RuntimeException {
    public AlreadyExistingExamException(String name) {
        super("L'esame inserito è già presente nel database " + name);
    }

    public AlreadyExistingExamException(String name, Throwable e) {
        super("L'esame inserito è già presente nel database " + name + "; " + e);
    }

}
