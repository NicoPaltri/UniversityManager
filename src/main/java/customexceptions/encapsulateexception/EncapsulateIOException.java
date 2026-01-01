package customexceptions.encapsulateexception;

import customexceptions.ApplicationException;

public class EncapsulateIOException extends ApplicationException {
    public EncapsulateIOException(String resource) {
        super("Impossibile accedere alla risorsa " + resource + " (INTERNAL ERROR)");
    }

    public EncapsulateIOException(String resource, Throwable e) {
        super("Impossibile accedere alla risorsa " + resource + " (INTERNAL ERROR); " + e.getMessage());
    }
}
