package examsmanager.examtypes;

import examsmanager.examfactories.ExamCreationData;

@FunctionalInterface
public interface ExamCreator<T extends Exam> {
    T create(ExamCreationData data);
}
