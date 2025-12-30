package dbmanager;

import customexceptions.accessdatasexception.DBFailedConnectionException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {
    private static final String DB_FOLDER = "data";
    private static final String DB_FILE = "votesDB.db";
    private static final String URL = "jdbc:sqlite:" + DB_FOLDER + "/" + DB_FILE;

    public static Connection getConnectionFromDB() {
        try {
            Files.createDirectories(
                    Paths.get(System.getProperty("user.dir"), DB_FOLDER)
            );

            Connection connection = DriverManager.getConnection(URL);

            Statement st = connection.createStatement();
            st.execute("PRAGMA foreign_keys = ON;");
            st.close();

            return connection;

        } catch (IOException | SQLException e) {
            throw new DBFailedConnectionException(URL, e);
        }
    }
}
