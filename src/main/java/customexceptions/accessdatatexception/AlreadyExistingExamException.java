package customexceptions.accessdatatexception;

public class AlreadyExistingExamException extends RuntimeException {
    public AlreadyExistingExamException() {
        super("L'esame inserito è già presente nel database ");
    }
}
