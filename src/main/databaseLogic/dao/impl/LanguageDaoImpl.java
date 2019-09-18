package databaseLogic.dao.impl;

import databaseLogic.connection.ConnectionPool;
import databaseLogic.dao.LanguageDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LanguageDaoImpl implements LanguageDao {


    private Connection connection;


    public LanguageDaoImpl() {
        connection = ConnectionPool.getConnection();
    }

    public LanguageDaoImpl(Connection connection) {
        this.connection = connection;

    }

    @Override
    public int getLanguageId(String language) {
        PreparedStatement statement = null;
        int result = 0;
        try {
            statement = connection.prepareStatement("SELECT id from language where language=?");
            statement.setString(1, language);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                result = rs.getInt("id");
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
    public String getLanguageById(int languageId) {
        PreparedStatement statement = null;
        String language = null;
        try {
            statement = connection.prepareStatement("SELECT language FROM language where id=?");
            statement.setInt(1, languageId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                language = rs.getString("language");
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
        return language;
    }

    @Override
    public void closeConnection() {
        ConnectionPool.closeConnection(connection);
    }
}
