package dbmanager.settingsTable;

import customexceptions.accessdatasexception.AlreadyExistingSettingException;
import customexceptions.accessdatasexception.DataAccessException;
import dbmanager.DBConnection;
import settingsmanager.Setting;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBSettingsInterrogation {
    public DBSettingsInterrogation() {
    }

    public boolean settingsTableIsFull() {
        String sql = "SELECT COUNT(*) FROM settings";

        try (Connection connection = DBConnection.getConnectionFromDB();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            rs.next();
            int dbRows = rs.getInt(1);

            return dbRows == ApplicationSettings.getAllDefaultSettings().size();

        } catch (SQLException e) {
            throw new DataAccessException(sql, e);
        }
    }

    public void fillSettingsTable() {
        String sql = "SELECT COUNT(*) FROM settings WHERE name = ?";
        List<Setting> defaultSettings = ApplicationSettings.getAllDefaultSettings();

        try (Connection conn = DBConnection.getConnectionFromDB();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            for (Setting setting : defaultSettings) {
                ps.setString(1, setting.getName());

                try (ResultSet rs = ps.executeQuery()) {
                    rs.next();

                    int count = rs.getInt(1);
                    if (count == 0) {
                        insertDefaultSetting(conn, setting);
                    }
                }
            }

        } catch (SQLException e) {
            throw new DataAccessException(sql, e);
        }
    }

    private void insertDefaultSetting(Connection conn, Setting setting) {
        String sql = "INSERT INTO settings (name, value) VALUES (?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, setting.getName());
            ps.setInt(2, setting.getValue());

            ps.executeUpdate();

        } catch (SQLException e) {
            if (e.getErrorCode() == 19) {
                throw new AlreadyExistingSettingException(setting.getName(), e);
            } else {
                throw new DataAccessException(sql, e);
            }
        }

    }


    public List<Setting> getAllPersonalizedSettings() {
        List<Setting> settings = new ArrayList<>();

        String sql = "SELECT name, value FROM settings";

        try (Connection connection = DBConnection.getConnectionFromDB();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String name = rs.getString("name");
                int value = rs.getInt("value");

                Setting setting = new Setting(name, value);
                settings.add(setting);
            }

        } catch (SQLException e) {
            throw new DataAccessException(sql, e);
        }

        return settings;
    }


    private int getValueFromSetting(String name) {
        String sql = "SELECT value FROM settings WHERE name = ?";

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
        return getValueFromSetting(ApplicationSettings.TOTAL_CFU.getSettingName());
    }


    private void changeSetting(String name, int value) {
        String sql = "UPDATE settings SET value = ? WHERE name = ?";

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
        changeSetting(ApplicationSettings.TOTAL_CFU.getSettingName(), CFU);
    }

}
