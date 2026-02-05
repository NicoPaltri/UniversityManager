package dbmanager.examsTable;

import customexceptions.accessdatasexception.ConstraintViolationException;
import customexceptions.accessdatasexception.DBInternalErrorException;
import customexceptions.examformatexception.UnknownExamTypeException;
import dbmanager.DBConnection;
import examsmanager.examtypes.Exam;
import examsmanager.examtypes.GradedExam;
import examsmanager.examtypes.Idoneita;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

public class DBManageExams {
    public void insertExam(Exam exam) {
        String sql = "INSERT INTO exams (name, weight, grade, exam_date, type) VALUES (?, ?, ?, ?, ?);";

        try (Connection conn = DBConnection.getConnectionFromDB();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, exam.getName());
            ps.setInt(2, exam.getWeight());

            if (exam instanceof GradedExam gradedExam) {
                ps.setInt(3, gradedExam.getGrade());
            } else if (exam instanceof Idoneita) {
                ps.setNull(3, Types.INTEGER);
            } else {
                throw new UnknownExamTypeException(exam.getName(), exam.getType());
            }

            ps.setString(4, exam.getDate().toString());
            ps.setString(5, exam.getType());

            ps.executeUpdate();

            System.out.println("Esame inserito con successo: " + exam.toString());
        } catch (SQLException e) {
            if (e.getErrorCode() == 19) {
                throw new ConstraintViolationException(exam.getName(), e);
            } else {
                throw new DBInternalErrorException(sql, e);
            }
        }
    }

    public void deleteExamByName(String name) {
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
