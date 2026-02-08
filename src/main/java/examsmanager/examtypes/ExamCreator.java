package examsmanager.examtypes;

import examsmanager.examfactories.ExamCreationRequest;

@FunctionalInterface
public interface ExamCreator<T extends Exam> {
    T createExam(ExamCreationRequest data);
}
