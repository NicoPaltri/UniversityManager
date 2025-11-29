package customexceptions.dateexception;

public class InvalidDateFormatException extends DateException {
    public InvalidDateFormatException(String name, String date) {
        super("Il formato della data in " + name + " non è aaaammgg [ " + date + "]");
    }
}
