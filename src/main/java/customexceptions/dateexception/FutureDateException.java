package customexceptions.dateexception;

import java.time.LocalDate;

public class FutureDateException extends DateException {
    public FutureDateException(String name, String date) {
        super("La data inserita in " + name + " è nel futuro [ " + date + " ]");
    }
}
