package customexceptions.accessdatasexception;

import customexceptions.ApplicationException;

public class DataAccessException extends ApplicationException {

    private static String buildMessage(String sql) {
        return "Errore di accesso ai dati durante la query " + sql;
    }

    public DataAccessException(String sql) {
        super(buildMessage(sql));
    }

    public DataAccessException(String sql, Throwable e) {
        super(buildMessage(sql), e);
    }

}