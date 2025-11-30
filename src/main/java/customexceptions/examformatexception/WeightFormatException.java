package customexceptions.examformatexception;

public class WeightFormatException extends RuntimeException {
    public WeightFormatException(String name) {
        super("Errore nella creazione dell'esame: peso non conforme [rules: 3<= x <=15] in " + name);
    }
}
