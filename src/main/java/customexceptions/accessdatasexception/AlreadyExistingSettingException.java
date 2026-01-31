package customexceptions.accessdatasexception;

import customexceptions.ApplicationException;

public class AlreadyExistingSettingException extends ApplicationException {

    public static String buildMessage(String name) {
        return "Violazione di un vincolo durante l'inserimento di " + name;
    }

    public AlreadyExistingSettingException(String name) {
        super(buildMessage(name));
    }

    public AlreadyExistingSettingException(String name, Throwable e) {
        super(buildMessage(name), e);
    }

}
