package customexceptions.accessdatasexception;

import customexceptions.ApplicationException;

public class DBFailedConnectionException extends ApplicationException {

    private static String buildMessage(String DB_URL) {
        return "Connessione al db" + DB_URL + " fallita";
    }

    public DBFailedConnectionException(String DB_URL) {
        super(buildMessage(DB_URL));
    }

    public DBFailedConnectionException(String DB_URL, Throwable e) {
        super(buildMessage(DB_URL) + e);
    }

}
