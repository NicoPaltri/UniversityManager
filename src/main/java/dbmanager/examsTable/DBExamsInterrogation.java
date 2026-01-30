package dbmanager.examsTable;

import customexceptions.accessdatasexception.DataAccessException;
import customexceptions.examformatexception.NullGradeForGradedExamException;
import customexceptions.examformatexception.UnknownExamTypeException;
import dbmanager.DBConnection;
import universitymanager.examfactories.GradedExamFactory;
import universitymanager.examfactories.IdoneitaFactory;
import universitymanager.examtypes.Exam;
import universitymanager.examtypes.ExamTypologies;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBExamsInterrogation {
    public List<Exam> getAllExams() {
        List<Exam> examList = new ArrayList<>();
        String sql = "SELECT name, weight, grade, exam_date, type FROM exams";

        GradedExamFactory gradedExamFactory = new GradedExamFactory();
        IdoneitaFactory idoneitaFactory = new IdoneitaFactory();

        try (Connection connection = DBConnection.getConnectionFromDB();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String name = rs.getString("name");
                int weight = rs.getInt("weight");

                //NULL-safe per Idoneità
                Integer grade;
                int temporaryGrade = rs.getInt("grade");
                grade = rs.wasNull() ? null : temporaryGrade;

                String date = rs.getString("exam_date");
                String type = rs.getString("type"); // nel DB è NOT NULL

                Exam exam;

                if (ExamTypologies.GradedExam.getExamTypology().equals(type)) {
                    if (grade == null) {
                        throw new NullGradeForGradedExamException(name);
                    }
                    exam = gradedExamFactory.createExam(name, weight, grade, date);

                } else if (ExamTypologies.Idoneita.getExamTypology().equals(type)) {
                    exam = idoneitaFactory.createExam(name, weight, date);

                } else {
                    throw new UnknownExamTypeException(name, type);
                }

                examList.add(exam);
            }

            return examList;

        } catch (SQLException e) {
            throw new DataAccessException(sql, e);
        }
    }
}
