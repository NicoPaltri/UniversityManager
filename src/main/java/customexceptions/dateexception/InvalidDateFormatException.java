package customexceptions.dateexception;

import customexceptions.ApplicationException;

public class InvalidDateFormatException extends ApplicationException {

    private static String buildMessage(String date) {
        return "Il formato della data non è corretto [ " + date + "]";
    }

    public InvalidDateFormatException(String date) {
        super(buildMessage(date));
    }

    public InvalidDateFormatException(String date, Throwable e) {
        super(buildMessage(date) + e);
    }

}