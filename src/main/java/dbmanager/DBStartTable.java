package dbmanager;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DBStartTable {
    private DBStartTable() {
    }

    public static void ensureCreated() {
        try (Connection conn = DBConnection.getConnection();
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
            throw new RuntimeException("Errore nella creazione della tabella exams", e);
        }
    }
}
