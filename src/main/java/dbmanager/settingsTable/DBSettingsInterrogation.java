package dbmanager.settingsTable;

import customexceptions.accessdatasexception.AlreadyExistingSettingException;
import customexceptions.accessdatasexception.DBInternalErrorException;
import customexceptions.accessdatasexception.DataAccessException;
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
            throw new DataAccessException(sql, e);
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
            throw new DataAccessException(sql, e);
        }
    }

    public void changeTotalCFU(int CFU) {
        if (CFU <= 0) {
            throw new IllegalArgumentException("CFU must be > 0");
        }

        changeSetting("totalCFU", CFU);
    }

    private void insertDefaultSetting(String name, int value) {
        String sql = "INSERT INTO settings (name, value) VALUES (?, ?);";

        try (Connection connection = DBConnection.getConnectionFromDB();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setInt(2, value);

            ps.executeUpdate();

        } catch (SQLException e) {
            if (e.getErrorCode() == 19) {
                throw new AlreadyExistingSettingException(name, e);
            } else {
                throw new DBInternalErrorException(sql, e);
            }
        }

    }

    public void insertDefaultCFU() {
        insertDefaultSetting("totalCFU", 180);
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
