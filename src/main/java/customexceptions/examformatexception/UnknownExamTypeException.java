package customexceptions.examformatexception;

import customexceptions.ApplicationException;

public class UnknownExamTypeException extends ApplicationException {
    public UnknownExamTypeException(String name, String unknownTypology) {
        super("Errore nella creazione dell'esame " + name + " [tipologia sconosciuta: " + unknownTypology + " ]");
    }
}
