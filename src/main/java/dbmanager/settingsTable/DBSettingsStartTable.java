package dbmanager.settingsTable;

import customexceptions.accessdatasexception.DBInternalErrorException;
import dbmanager.DBConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DBSettingsStartTable {

    public static void ensureCreated() {
        try (Connection conn = DBConnection.getConnectionFromDB();
             Statement st = conn.createStatement()) {

            st.execute("""
                        CREATE TABLE IF NOT EXISTS settings(
                          name      TEXT PRIMARY KEY,
                          value     INTEGER NOT NULL
                        );
                    """);
        } catch (SQLException e) {
        throw new DBInternalErrorException("Errore nella creazione della tabella settings", e);
        }
    }
}
