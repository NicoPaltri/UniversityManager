package customexceptions.dateexception;

import java.time.LocalDate;

public class FutureDateException extends DateException {
    public FutureDateException(String date) {
        super("La data inserita è nel futuro [ " + date + " ]");
    }
}
