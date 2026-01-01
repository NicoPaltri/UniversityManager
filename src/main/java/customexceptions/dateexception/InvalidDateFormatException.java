package customexceptions.dateexception;

import customexceptions.ApplicationException;

public class InvalidDateFormatException extends ApplicationException {
    public InvalidDateFormatException(String name, String date) {
        super("Il formato della data non è aaaammgg [ " + date + "], in " + name);
    }

    public InvalidDateFormatException(String name, String date, Throwable e) {
        super("Il formato della data non è aaaammgg [ " + date + "], in " + name + "; " + e);
    }
}
