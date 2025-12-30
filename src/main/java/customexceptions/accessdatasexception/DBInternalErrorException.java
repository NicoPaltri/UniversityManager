package customexceptions.accessdatasexception;

public class DBInternalErrorException extends RuntimeException {
    public DBInternalErrorException(String sql, Throwable e) {
        super("Errore interno al DB durante la query " + sql + "; " + e);
    }
}
