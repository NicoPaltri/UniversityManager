package customexceptions.accessdatatexception;

public class DBFailedConnectionException extends RuntimeException {
    public DBFailedConnectionException(String message) {
        super("La connessione al db non è riuscita " + message);
    }
}
