package examsmanager.examtypes;

import examsmanager.examfactories.ExamCreationData;

@FunctionalInterface
public interface ExamCreator {
    Exam create(ExamCreationData data);
}
