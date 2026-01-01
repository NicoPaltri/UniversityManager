package customexceptions.accessdatasexception;

import customexceptions.ApplicationException;

public class DataAccessException extends ApplicationException {
    public DataAccessException(String sql, Throwable e) {
        super("Errore di accesso ai dati durante la query " + sql + "; " + e);
    }
}
