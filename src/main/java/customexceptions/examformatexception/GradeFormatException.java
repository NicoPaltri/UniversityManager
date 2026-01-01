package customexceptions.examformatexception;

import customexceptions.ApplicationException;

public class GradeFormatException extends ApplicationException {
    public GradeFormatException(String name) {
        super("Errore nella creazione dell'esame: voto non conforme [rules: 18<= x <=33] in " + name);
    }
}
