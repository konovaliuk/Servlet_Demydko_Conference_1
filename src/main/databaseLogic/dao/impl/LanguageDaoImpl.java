package databaseLogic.dao.impl;

import databaseLogic.connection.ConnectionPool;
import databaseLogic.dao.LanguageDao;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LanguageDaoImpl implements LanguageDao {
    private Logger logger = Logger.getLogger(LanguageDaoImpl.class);
    private Connection connection;

    public LanguageDaoImpl() {
        connection = ConnectionPool.getConnection();
    }

    public LanguageDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public int getLanguageId(String language) {

        int result = 0;
        try (PreparedStatement statement = connection.prepareStatement("SELECT id from language where language=?")) {
            statement.setString(1, language);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                result = rs.getInt("id");
            }
        } catch (SQLException e) {
            logger.error(e);
        }
        return result;
    }

    @Override
    public String getLanguageById(int languageId) {
        String language = null;
        try (PreparedStatement statement = connection.prepareStatement("SELECT language FROM language where id=?")) {
            statement.setInt(1, languageId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                language = rs.getString("language");
            }
        } catch (SQLException e) {
            logger.error(e);
        }
        return language;
    }

    @Override
    public void closeConnection() {
        ConnectionPool.closeConnection(connection);
    }
}
