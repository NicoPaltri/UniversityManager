package customexceptions.dateexception;

import customexceptions.ApplicationException;

import java.time.LocalDate;

public class FutureDateException extends ApplicationException {

    private static String buildMessage(String name, LocalDate date) {
        return "La data inserita è nel futuro [ " + date.toString() + " ], in " + name;
    }

    public FutureDateException(String name, LocalDate date) {
        super(buildMessage(name, date));
    }

    public FutureDateException(String name, LocalDate date, Throwable e) {
        super(buildMessage(name, date), e);
    }

}
