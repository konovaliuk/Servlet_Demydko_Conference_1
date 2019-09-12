package databaseLogic.dao.impl;

import databaseLogic.connection.DataSourceConference;
import databaseLogic.dao.PresenceDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PresenceDaoImpl implements PresenceDao {

    private DataSourceConference dataSource;                             // todo
    private Connection connection;
    // private TestDataSource dataSource;

    public PresenceDaoImpl() {
        dataSource = DataSourceConference.getInstance();
        this.connection = dataSource.getConnection();
        // dataSource = new TestDataSource();
    }

    @Override
    public int addPresence(Long reportId, int count) {
        PreparedStatement statement = null;
        int result = 0;
        try {
            if (isReportPresent(reportId)) {
                statement = connection.prepareStatement("UPDATE presence set count=? where reportId=?");
                statement.setInt(1, count);
                statement.setLong(2, reportId);
                result = statement.executeUpdate();
            } else {
                statement = connection.prepareStatement("INSERT presence(reportId, count) values (?,?)");
                statement.setLong(1, reportId);
                statement.setInt(2, count);
                result = statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null)
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }
        return result;
    }

    @Override
    public int getPresence(Long reportId) {
        PreparedStatement statement = null;
        int result = 0;
        try {
            statement = connection.prepareStatement("SELECT count from presence where reportId=?");
            statement.setLong(1, reportId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                result = rs.getInt("count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null)
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }
        return result;
    }

    private boolean isReportPresent(Long reportId) {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("SELECT reportId FROM presence where reportId=?");
            statement.setLong(1, reportId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null)
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }
        return false;
    }

    @Override
    public void closeConnection() {
        dataSource.closeConnection();
    }
}
