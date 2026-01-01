package dbmanager.examsTable;

import customexceptions.accessdatasexception.DBInternalErrorException;
import dbmanager.DBConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DBExamsStartTable {

    public static void ensureCreated() {
        try (Connection conn = DBConnection.getConnectionFromDB();
             Statement st = conn.createStatement()) {

            st.execute("""
                        CREATE TABLE IF NOT EXISTS exams(
                          name      TEXT PRIMARY KEY,
                          weight    INTEGER NOT NULL CHECK(weight BETWEEN 3 AND 15),
                          grade     INTEGER NOT NULL CHECK(grade BETWEEN 18 AND 30),
                          exam_date TEXT NOT NULL CHECK(length(exam_date) = 8)
                        );
                    """);
        } catch (SQLException e) {
            throw new DBInternalErrorException("Errore nella creazione della tabella exams", e);
        }
    }
}
