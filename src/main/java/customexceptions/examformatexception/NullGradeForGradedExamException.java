package customexceptions.examformatexception;

import customexceptions.ApplicationException;

public class NullGradeForGradedExamException extends ApplicationException {

    private static String buildMessage(String name) {
        return "Grade was NULL for a graded exam [name=" + name + "]";
    }

    public NullGradeForGradedExamException(String name) {
        super(buildMessage(name));
    }

    public NullGradeForGradedExamException(String name, Throwable e) {
        super(buildMessage(name), e);
    }

}
