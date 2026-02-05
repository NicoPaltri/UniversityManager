package customexceptions.examexceptions;

import customexceptions.ApplicationException;

public class UnknownExamTypeException extends ApplicationException {

    private static String buildMessage(String name, String unknownTypology) {
        return "Errore nella creazione dell'esame " + name + " [tipologia sconosciuta: " + unknownTypology + " ]";
    }

    public UnknownExamTypeException(String name, String unknownTypology) {
        super(buildMessage(name, unknownTypology));
    }

    public UnknownExamTypeException(String name, String unknownTypology, Throwable e) {
        super(buildMessage(name, unknownTypology), e);
    }

}
