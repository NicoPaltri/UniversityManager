package customexceptions.settingsexcpetions;

public class InvalidCFUValueException extends RuntimeException {
    public InvalidCFUValueException() {
        super("Il numero totale di CFU non può essere minore o uguale a 0");
    }
}
