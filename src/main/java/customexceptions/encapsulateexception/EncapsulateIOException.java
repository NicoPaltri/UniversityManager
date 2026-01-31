package customexceptions.encapsulateexception;

import customexceptions.ApplicationException;

public class EncapsulateIOException extends ApplicationException {

    private static String buildMessage(String resource) {
        return "Impossibile accedere alla risorsa " + resource + " (INTERNAL ERROR)";
    }

    public EncapsulateIOException(String resource) {
        super(buildMessage(resource));
    }

    public EncapsulateIOException(String resource, Throwable e) {
        super(buildMessage(resource), e);
    }

}
