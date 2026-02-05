package dbmanager.examsTable;

import application.applicationutils.ExamUtils;
import customexceptions.accessdatasexception.ConstraintViolationException;
import customexceptions.accessdatasexception.DBInternalErrorException;
import customexceptions.examexceptions.UnknownExamTypeException;
import dbmanager.DBConnection;
import examsmanager.examfactories.ExamCreationData;
import examsmanager.examfactories.GradedExamFactory;
import examsmanager.examfactories.IdoneitaFactory;
import examsmanager.examtypes.Exam;
import examsmanager.examtypes.ExamTypologies;
import examsmanager.examtypes.GradedExam;
import examsmanager.examtypes.Idoneita;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DBExamRepository {
    public List<Exam> getAll() {
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

                Integer grade = (Integer) rs.getObject("grade");

                String stringDate = rs.getString("exam_date");
                LocalDate date = ExamUtils.parseIsoDate(stringDate);

                String type = rs.getString("type");
                ExamTypologies typology = ExamTypologies.fromType(name, type);

                Exam exam;
                ExamCreationData data = new ExamCreationData(name, weight, date);

                switch (typology) {
                    case GradedExam -> {
                        data.withGrade(grade);
                        exam = gradedExamFactory.createExam(data);
                    }

                    case Idoneita -> {
                        exam = idoneitaFactory.createExam(data);
                    }

                    default -> throw new UnknownExamTypeException(name, type);

                }

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

            //DB CONOSCE DOMINIO: PROBLEMA
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
