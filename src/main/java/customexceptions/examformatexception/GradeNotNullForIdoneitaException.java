package customexceptions.examformatexception;

import customexceptions.ApplicationException;

public class GradeNotNullForIdoneitaException extends ApplicationException {
    private static String buildMessage(String name) {
        return "Voto not null per un esame di tipo Idoneita (nome=" + name + ")";
    }

    public GradeNotNullForIdoneitaException(String name) {
        super(buildMessage(name));
    }

    public GradeNotNullForIdoneitaException(String name, Throwable e) {
        super(buildMessage(name), e);
    }
}
