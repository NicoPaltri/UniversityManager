package customexceptions.examformatexception;

import customexceptions.ApplicationException;

public class GradeFormatException extends ApplicationException {

    private static String buildMessage(String name) {
        return "Errore nella creazione dell'esame: voto non conforme [rules: 18<= x <=33] in " + name;
    }

    public GradeFormatException(String name) {
        super(buildMessage(name));
    }

    public GradeFormatException(String name, Throwable e) {
        super(buildMessage(name), e);
    }

}
