package customexceptions.accessdatasexception;

public class AlreadyExistingSettingException extends RuntimeException {
    public AlreadyExistingSettingException(String name) {
        super("Il setting inserito è già presente nel database " + name);
    }

    public AlreadyExistingSettingException(String name, Throwable e) {
        super("Il setting inserito è già presente nel database " + name + "; " + e);
    }

}
