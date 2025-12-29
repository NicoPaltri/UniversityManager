package customexceptions.accessdataexception;

public class AlreadyExistingSettingException extends RuntimeException {
    public AlreadyExistingSettingException() {
        super("Il setting inserito è già presente nel database");
    }
}
