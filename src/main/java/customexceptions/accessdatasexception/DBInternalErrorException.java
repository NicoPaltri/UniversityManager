package customexceptions.accessdatasexception;

import customexceptions.ApplicationException;

public class DBInternalErrorException extends ApplicationException {
    public DBInternalErrorException(String sql, Throwable e) {
        super("Errore interno al DB durante la query " + sql + "; " + e);
    }
}
