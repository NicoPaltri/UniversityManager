package customexceptions.dateexception;

import customexceptions.ApplicationException;

public class InvalidDateFormatException extends ApplicationException {
    public InvalidDateFormatException(String date) {
        super("Il formato della data non è corretto [ " + date + "]");
    }

    public InvalidDateFormatException(String date, Throwable e) {
        super("Il formato della data non è corretto [ " + date + "]" + "; " + e);
    }
}