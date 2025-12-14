package dbmanager;

import customexceptions.accessdatatexception.AlreadyExistingExamException;
import customexceptions.accessdatatexception.DBFailedConnectionException;
import customexceptions.accessdatatexception.DataAccessException;
import universitymanager.Exam;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBManageDB {
    public static void insertExam(Exam exam) {
        String sql = "INSERT INTO exams (name, weight, grade, exam_date) VALUES (?, ?, ?, ?);";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, exam.getName());
            ps.setInt(2, exam.getWeight());
            ps.setInt(3, exam.getGrade());
            ps.setString(4, exam.getDate());

            ps.executeUpdate();

            System.out.println("Esame inserito con successo: " + exam.toString());
        } catch (SQLException e) {
            e.printStackTrace();

            // CONTROLLO DEL TIPO DI ERRORE
            // Il codice 19 corrisponde a SQLITE_CONSTRAINT (es. PK duplicata)
            if (e.getErrorCode() == 19) {
                throw new AlreadyExistingExamException();
            }
            else {
                // Qualsiasi altro codice (es. 0, 1, 5, 6...) è un problema tecnico/di connessione
                // L'utente non può farci nulla se non riprovare più tardi
                throw new DBFailedConnectionException("");
            }
        }
    }

    public static void deleteExamByName(String name) {
        String sql = "DELETE FROM exams WHERE name = ?;";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, name);
            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Exam with name " + name + " removed successfully.");
            } else {
                System.out.println("No exam was found with name " + name + ".");
            }

        } catch (SQLException e) {
            throw new DataAccessException(sql);
        }
    }
}
