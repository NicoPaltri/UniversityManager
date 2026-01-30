package universitymanager.examfactories;

import universitymanager.examtypes.Idoneita;

public class IdoneitaFactory extends ExamFactory{
    public IdoneitaFactory() {
    }

    public Idoneita createExam(String name, int weight, String date){
        super.checkExam(name,weight,date);

        return new Idoneita(name, weight, date);
    }
}
