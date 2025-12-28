package dbmanager.settingsTable;

import customexceptions.accessdataexception.DataAccessException;
import dbmanager.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBSettingsInterrogation {
    public DBSettingsInterrogation() {
    }

    public int getTotalAmountCFU() {
        String sql = "SELECT value FROM settings WHERE name = 'totalCFU';";

        try (Connection connection = DBConnection.getConnectionFromDB();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            return rs.getInt("value");

        } catch (SQLException e) {
            throw new DataAccessException(sql);
        }
    }

    public void changeTotalCFU(int CFU) {
        if (CFU <= 0) {
            throw new IllegalArgumentException("CFU must be > 0");
        }

        String sql = "UPDATE settings SET value = ? WHERE name = 'totalCFU';";

        try (Connection conn = DBConnection.getConnectionFromDB();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, CFU);

            ps.executeUpdate();

            System.out.println("Modifica avvenuta con successo, nuovi CFU: " + CFU);
        } catch (SQLException e) {
            throw new DataAccessException(sql);
        }
    }

    public void setDefaultCFU() {
        String sql = "INSERT INTO settings (name, value) VALUES (?, ?);";

        try (Connection connection = DBConnection.getConnectionFromDB();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, "totalCFU");
            ps.setInt(2, 180);

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DataAccessException(sql);
        }
    }


    public boolean settingsTableExistsAndIsFull() {
        String sql = "SELECT 1 FROM settings";

        try (Connection connection = DBConnection.getConnectionFromDB();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            return rs.next();

        } catch (SQLException e) {
            return false;
        }
    }

}
