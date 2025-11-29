package customexceptions.dateexception;

public class DateException extends RuntimeException {
    public DateException(String date) {
        super(date);
    }
}
