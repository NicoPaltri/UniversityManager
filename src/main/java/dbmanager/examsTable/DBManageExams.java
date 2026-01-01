package dbmanager.examsTable;

import customexceptions.accessdatasexception.AlreadyExistingExamException;
import customexceptions.accessdatasexception.DBInternalErrorException;
import dbmanager.DBConnection;
import universitymanager.Exam;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBManageExams {
    public static void insertExam(Exam exam) {
        String sql = "INSERT INTO exams (name, weight, grade, exam_date) VALUES (?, ?, ?, ?);";

        try (Connection conn = DBConnection.getConnectionFromDB();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, exam.getName());
            ps.setInt(2, exam.getWeight());
            ps.setInt(3, exam.getGrade());
            ps.setString(4, exam.getDate());

            ps.executeUpdate();

            System.out.println("Esame inserito con successo: " + exam.toString());
        } catch (SQLException e) {
            if (e.getErrorCode() == 19) {
                throw new AlreadyExistingExamException(exam.getName(), e);
            } else {
                throw new DBInternalErrorException(sql, e);
            }
        }
    }

    public static void deleteExamByName(String name) {
        String sql = "DELETE FROM exams WHERE name = ?;";

        try (Connection conn = DBConnection.getConnectionFromDB();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, name);
            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Exam with name " + name + " removed successfully.");
            } else {
                System.out.println("No exam was found with name " + name + ".");
            }

        } catch (SQLException e) {
            throw new DBInternalErrorException(sql, e);
        }
    }
}
