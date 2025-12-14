package customexceptions.dateexception;

public class InvalidDateFormatException extends DateException {
    public InvalidDateFormatException(String date) {
        super("Il formato della data non è aaaammgg [ " + date + "]");
    }
}
