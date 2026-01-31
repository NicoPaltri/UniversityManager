package universitymanager.examfactories;

import universitymanager.examtypes.Idoneita;

import java.time.LocalDate;

public class IdoneitaFactory extends ExamFactory{
    public IdoneitaFactory() {
    }

    public Idoneita createExam(String name, int weight, LocalDate date){
        super.checkExam(name,weight,date);

        return new Idoneita(name, weight, date);
    }
}
