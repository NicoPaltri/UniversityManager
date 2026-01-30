package customexceptions.examformatexception;

import customexceptions.ApplicationException;

public class NullGradeForGradedExamException extends ApplicationException {
    public NullGradeForGradedExamException(String name) {
        super("Grade was NULL for a graded exam [name=" + name + "]");
    }
}
