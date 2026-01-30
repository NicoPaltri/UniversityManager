package customexceptions.examformatexception;

import customexceptions.ApplicationException;

public class TypeFormatException extends ApplicationException {
    public TypeFormatException(String name, String unknownTypology) {
        super("Errore nella creazione dell'esame " + name + " [tipologia sconosciuta: " + unknownTypology + " ]");
    }
}
