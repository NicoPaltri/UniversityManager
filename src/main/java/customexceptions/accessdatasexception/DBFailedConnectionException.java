package customexceptions.accessdatasexception;

public class DBFailedConnectionException extends RuntimeException {
    public DBFailedConnectionException(String DB_URL, Throwable e) {
        super("Connessione al db" + DB_URL + ", esito: fallita; " + e);
    }
}
