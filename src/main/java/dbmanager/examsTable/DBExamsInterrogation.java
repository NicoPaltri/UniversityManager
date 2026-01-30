package dbmanager.examsTable;

import customexceptions.accessdatasexception.DataAccessException;
import dbmanager.DBConnection;
import universitymanager.examtypes.Exam;
import universitymanager.examfactories.ExamFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBExamsInterrogation {
    public DBExamsInterrogation() {
    }

    public List<Exam> getAllExams() {
        List<Exam> examList = new ArrayList<>();
        ExamFactory examFactory = new ExamFactory();

        String sql = "SELECT name, weight, grade, exam_date, type FROM exams";

        try (Connection connection = DBConnection.getConnectionFromDB();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String name = rs.getString("name");
                int weight = rs.getInt("weight");
                int grade = rs.getInt("grade");
                String date = rs.getString("exam_date");
                String type=rs.getString("type");

                Exam exam = examFactory.createExam(name, weight, grade, date);
                examList.add(exam);
            }

        } catch (SQLException e) {
            throw new DataAccessException(sql, e);
        }

        return examList;
    }
}