package dbmanager.examsTable;

import application.applicationutils.ExamUtils;
import customexceptions.accessdatasexception.ConstraintViolationException;
import customexceptions.accessdatasexception.DBInternalErrorException;
import dbmanager.DBConnection;
import examsmanager.examfactories.ExamCreationData;
import examsmanager.examtypes.Exam;
import examsmanager.examtypes.ExamTypologies;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DBExamRepository {
    public List<Exam> getAll() {
        List<Exam> examList = new ArrayList<>();
        String sql = "SELECT name, weight, grade, exam_date, type FROM exams";

        try (Connection connection = DBConnection.getConnectionFromDB();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String name = rs.getString("name");
                int weight = rs.getInt("weight");

                Integer rawGrade = (Integer) rs.getObject("grade");
                Optional<Integer> grade = Optional.ofNullable(rawGrade);

                String stringDate = rs.getString("exam_date");
                LocalDate date = ExamUtils.parseIsoDate(stringDate);

                String type = rs.getString("type");
                ExamTypologies typology = ExamTypologies.fromType(name, type);

                ExamCreationData data = new ExamCreationData(name, weight, date);
                if (grade.isPresent()) {
                    data.withGrade(grade.get());
                }

                Exam exam = typology.create(data);

                examList.add(exam);
            }

            return examList;

        } catch (SQLException e) {
            throw new DBInternalErrorException(sql, e);
        }
    }

    public void insert(Exam exam) {
        String sql = "INSERT INTO exams (name, weight, grade, exam_date, type) VALUES (?, ?, ?, ?, ?);";

        try (Connection conn = DBConnection.getConnectionFromDB();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, exam.getName());
            ps.setInt(2, exam.getWeight());

            Optional<Integer> grade = exam.getGrade();
            if (grade.isEmpty()) {
                ps.setInt(3, Types.NULL);
            } else {
                ps.setInt(3, grade.get());
            }

            ps.setString(4, exam.getDate().toString());
            ps.setString(5, exam.getType());

            ps.executeUpdate();

        } catch (SQLException e) {
            if (e.getErrorCode() == 19) {
                throw new ConstraintViolationException(exam.getName(), e);
            } else {
                throw new DBInternalErrorException(sql, e);
            }
        }
    }

    public void deleteByName(String name) {
        String sql = "DELETE FROM exams WHERE name = ?;";

        try (Connection conn = DBConnection.getConnectionFromDB();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DBInternalErrorException(sql, e);
        }
    }
}
