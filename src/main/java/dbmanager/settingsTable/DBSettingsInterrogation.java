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

    private int getValueFromSetting(String name) {
        String sql = "SELECT value FROM settings WHERE name = ?;";

        try (Connection connection = DBConnection.getConnectionFromDB();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();

            return rs.getInt("value");

        } catch (SQLException e) {
            throw new DataAccessException(sql);
        }
    }

    public int getTotalAmountCFU() {
        return getValueFromSetting("totalCFU");
    }

    private void changeSetting(String name, int value) {
        String sql = "UPDATE settings SET value = ? WHERE name = ?;";

        try (Connection conn = DBConnection.getConnectionFromDB();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, value);
            ps.setString(2, name);

            ps.executeUpdate();

            System.out.println("Modifica avvenuta con successo: " + sql);
        } catch (SQLException e) {
            throw new DataAccessException(sql);
        }
    }

    public void changeTotalCFU(int CFU) {
        if (CFU <= 0) {
            throw new IllegalArgumentException("CFU must be > 0");
        }

        changeSetting("totalCFU", CFU);
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
