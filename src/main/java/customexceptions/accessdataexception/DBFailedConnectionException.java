package customexceptions.accessdataexception;

public class DBFailedConnectionException extends RuntimeException {
    public DBFailedConnectionException(String message) {
        super("La connessione al db non è riuscita " + message);
    }
}
