package customexceptions.examformatexception;

public class GradeFormatException extends RuntimeException {
    public GradeFormatException(String name) {
        super("Errore nella creazione dell'esame: voto non conforme [rules: 18<= x <=33] in " + name);
    }
}
