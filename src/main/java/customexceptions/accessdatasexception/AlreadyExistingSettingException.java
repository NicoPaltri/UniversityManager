package customexceptions.accessdatasexception;

import customexceptions.ApplicationException;

public class AlreadyExistingSettingException extends ApplicationException {

    public static String buildMessage(String name) {
        return "Il setting inserito è già presente nel database " + name;
    }

    public AlreadyExistingSettingException(String name) {
        super(buildMessage(name));
    }

    public AlreadyExistingSettingException(String name, Throwable e) {
        super(buildMessage(name), e);
    }

}
