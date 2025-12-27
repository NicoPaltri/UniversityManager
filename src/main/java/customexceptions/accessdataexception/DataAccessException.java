package customexceptions.accessdataexception;

public class DataAccessException extends RuntimeException {
    public DataAccessException(String query) {
        super("Errore di accesso ai dati durante la query [query: " + query + "]");
    }
}
