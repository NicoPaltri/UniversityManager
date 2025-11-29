package dbmanager;

import customexceptions.accessdatatexception.DataAccessException;
import universitymanager.Exam;
import universitymanager.ExamFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBExamInterrogation {
    public DBExamInterrogation() {
    }

    public List<Exam> getAllExams() {
        List<Exam> examList = new ArrayList<>();
        ExamFactory examFactory = new ExamFactory();

        String sql = "SELECT name, weight, grade, exam_date FROM exams";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String name = rs.getString("name");
                int weight = rs.getInt("weight");
                int grade = rs.getInt("grade");
                String date = rs.getString("exam_date");

                Exam exam = examFactory.createExam(name, weight, grade, date);
                examList.add(exam);
            }

        } catch (SQLException e) {
            throw new DataAccessException(sql);
        }

        return examList;
    }
}