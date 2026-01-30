package dbmanager.examsTable;

import customexceptions.accessdatasexception.AlreadyExistingExamException;
import customexceptions.accessdatasexception.DBInternalErrorException;
import customexceptions.examformatexception.NullGradeForGradedExamException;
import customexceptions.examformatexception.TypeFormatException;
import dbmanager.DBConnection;
import universitymanager.examtypes.Exam;
import universitymanager.examtypes.ExamTypologies;
import universitymanager.examtypes.GradedExam;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

public class DBManageExams {
    public static void insertExam(Exam exam) {
        String sql = "INSERT INTO exams (name, weight, grade, exam_date, type) VALUES (?, ?, ?, ?, ?);";

        try (Connection conn = DBConnection.getConnectionFromDB();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, exam.getName());
            ps.setInt(2, exam.getWeight());

            if (ExamTypologies.GradedExam.getExamTypology().equals(exam.getType())) {
                //ACCOPPIAMENTO
                GradedExam gradedExam = (GradedExam) exam;
                ps.setInt(3, gradedExam.getGrade());
            } else if (ExamTypologies.Idoneita.getExamTypology().equals(exam.getType())) {
                ps.setNull(3, Types.INTEGER);
            } else {
                throw new TypeFormatException(exam.getName(), exam.getType());
            }

            ps.setString(4, exam.getDate());
            ps.setString(5, exam.getType());

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
