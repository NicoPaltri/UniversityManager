package customexceptions.accessdatasexception;

import customexceptions.ApplicationException;

public class AlreadyExistingSettingException extends ApplicationException {
    public AlreadyExistingSettingException(String name) {
        super("Il setting inserito è già presente nel database " + name);
    }

    public AlreadyExistingSettingException(String name, Throwable e) {
        super("Il setting inserito è già presente nel database " + name + "; " + e);
    }

}
