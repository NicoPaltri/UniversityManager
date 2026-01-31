package customexceptions.accessdatasexception;

import customexceptions.ApplicationException;

public class DBInternalErrorException extends ApplicationException {

    private static String buildMessage(String sql) {
        return "Errore interno al DB durante la query " + sql;
    }

    public DBInternalErrorException(String sql) {
        super(buildMessage(sql));
    }

    public DBInternalErrorException(String sql, Throwable e) {
        super(buildMessage(sql), e);
    }

}
