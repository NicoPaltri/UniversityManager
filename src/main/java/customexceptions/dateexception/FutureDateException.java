package customexceptions.dateexception;

import java.time.LocalDate;

public class FutureDateException extends RuntimeException {
    public FutureDateException(String name, String date) {
        super("La data inserita è nel futuro [ " + date + " ], in " + name);
    }

    public FutureDateException(String name, String date, Throwable e) {
        super("La data inserita è nel futuro [ " + date + " ], in " + name + "; " + e);

    }
}
