package customexceptions.examformatexception;

import customexceptions.ApplicationException;

public class WeightFormatException extends ApplicationException {
    public WeightFormatException(String name) {
        super("Errore nella creazione dell'esame: peso non conforme [rules: 3<= x <=15] in " + name);
    }
}
