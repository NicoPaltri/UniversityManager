package customexceptions.settingsexcpetions;

import customexceptions.ApplicationException;

public class UnknownSettingException extends ApplicationException {
    private static String buildMessage(String name) {
        return "Il setting passato (" + name + ") non è stato riconosciuto";
    }

    public UnknownSettingException(String name) {
        super(buildMessage(name));
    }

    public UnknownSettingException(String name, Throwable e) {
        super(buildMessage(name), e);
    }
}
