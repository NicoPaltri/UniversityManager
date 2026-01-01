package customexceptions.accessdatasexception;

import customexceptions.ApplicationException;

public class DBFailedConnectionException extends ApplicationException {
    public DBFailedConnectionException(String DB_URL, Throwable e) {
        super("Connessione al db" + DB_URL + ", esito: fallita; " + e);
    }
}
