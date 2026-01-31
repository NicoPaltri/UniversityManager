package customexceptions.dateexception;

import customexceptions.ApplicationException;

import java.time.LocalDate;

public class FutureDateException extends ApplicationException {
    public FutureDateException(String name, LocalDate date) {
        super("La data inserita è nel futuro [ " + date.toString() + " ], in " + name);
    }

    public FutureDateException(String name, LocalDate date, Throwable e) {
        super("La data inserita è nel futuro [ " + date + " ], in " + name + "; " + e);
    }
}
