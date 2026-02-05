package customexceptions.examexceptions;

import customexceptions.ApplicationException;

public class WeightFormatException extends ApplicationException {

    private static String buildMessage(String name) {
        return "Errore nella creazione dell'esame: peso non conforme [rules: 3<= x <=15] in " + name;
    }

    public WeightFormatException(String name) {
        super(buildMessage(name));
    }

    public WeightFormatException(String name, Throwable e) {
        super(buildMessage(name), e);
    }

}
