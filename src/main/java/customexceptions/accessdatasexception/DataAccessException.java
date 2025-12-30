package customexceptions.accessdatasexception;

public class DataAccessException extends RuntimeException {
    public DataAccessException(String sql, Throwable e) {
        super("Errore di accesso ai dati durante la query " + sql + "; " + e);
    }
}
