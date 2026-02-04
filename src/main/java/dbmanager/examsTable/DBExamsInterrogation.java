package dbmanager.examsTable;

import application.applicationutils.ExamUtils;
import customexceptions.accessdatasexception.DBInternalErrorException;
import customexceptions.examformatexception.UnknownExamTypeException;
import dbmanager.DBConnection;
import examsmanager.examfactories.ExamCreationData;
import examsmanager.examfactories.GradedExamFactory;
import examsmanager.examfactories.IdoneitaFactory;
import examsmanager.examtypes.Exam;
import examsmanager.examtypes.ExamTypologies;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
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

                String stringDate = rs.getString("exam_date");
                LocalDate date = ExamUtils.parseIsoDate(stringDate);

                String type = rs.getString("type");

                Exam exam;
                ExamCreationData data = new ExamCreationData(name, weight, date);

                if (ExamTypologies.GradedExam.getExamTypology().equals(type)) {
                    data.withGrade(grade);

                    exam = gradedExamFactory.createExam(data);

                } else if (ExamTypologies.Idoneita.getExamTypology().equals(type)) {
                    exam = idoneitaFactory.createExam(data);

                } else {
                    throw new UnknownExamTypeException(name, type);
                }

                examList.add(exam);
            }

            return examList;

        } catch (SQLException e) {
            throw new DBInternalErrorException(sql, e);
        }
    }
}
