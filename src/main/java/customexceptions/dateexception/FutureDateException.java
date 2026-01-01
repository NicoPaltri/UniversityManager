package customexceptions.dateexception;

import customexceptions.ApplicationException;

public class FutureDateException extends ApplicationException {
    public FutureDateException(String name, String date) {
        super("La data inserita è nel futuro [ " + date + " ], in " + name);
    }

    public FutureDateException(String name, String date, Throwable e) {
        super("La data inserita è nel futuro [ " + date + " ], in " + name + "; " + e);

    }
}
