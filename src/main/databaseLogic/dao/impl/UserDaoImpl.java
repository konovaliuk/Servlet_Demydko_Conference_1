package databaseLogic.dao.impl;

import databaseLogic.connection.ConnectionPool;
import databaseLogic.dao.LanguageDao;
import databaseLogic.dao.PositionDao;
import databaseLogic.dao.UserDao;
import databaseLogic.factory.DaoFactory;
import entity.User;
import org.apache.log4j.Logger;
import java.sql.*;

public class UserDaoImpl implements UserDao {
    private Logger logger = Logger.getLogger(UserDaoImpl.class);
    private Connection connection;

    public UserDaoImpl() {
        connection = ConnectionPool.getConnection();
    }

    public UserDaoImpl(Connection connection) {
        this.connection = connection;
    }


    @Override
    public Long addUser(User user) {
        Long id = null;
        LanguageDao languageDao = DaoFactory.getLanguageDao(connection);
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT users(name, surname, email, password, language)"
                        + " values (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getSurname());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getPassword());
            statement.setInt(5, languageDao.getLanguageId(user.getLanguage()));
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            rs.next();
            id = rs.getLong(1);
        } catch (SQLException e) {
            logger.error(e);
        }
        return id;
    }

    @Override
    public User getUserByEmail(String email) {
        User user = null;
        PositionDao positionDao = DaoFactory.getPositionDao(connection);
        LanguageDao languageDao = DaoFactory.getLanguageDao(connection);
        try (PreparedStatement statement = connection.prepareStatement("SELECT * from users where email=?")) {
            statement.setString(1, email);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                user = new User();
                user.setId(rs.getLong("id"));
                user.setName(rs.getString("name"));
                user.setSurname(rs.getString("surname"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                int position = rs.getInt("position");
                user.setPosition(positionDao.getPosition(position));
                user.setLanguage(languageDao.getLanguageById(rs.getInt("language")));
            }
        } catch (SQLException e) {
            logger.error(e);
        }
        return user;
    }

    @Override
    public User getUserById(Long id) {
        User user = null;
        PositionDao positionDao = DaoFactory.getPositionDao(connection);
        LanguageDao languageDao = DaoFactory.getLanguageDao(connection);
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE id=?")) {
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                user = new User();
                user.setId(id);
                user.setName(rs.getString("name"));
                user.setSurname(rs.getString("surname"));
                user.setEmail(rs.getString("email"));
                user.setPassword("password");
                user.setPosition(positionDao.getPosition(rs.getInt("position")));
                user.setLanguage(languageDao.getLanguageById(rs.getInt("language")));
            }
        } catch (SQLException e) {
            logger.error(e);
        }
        return user;
    }

    @Override
    public void closeConnection() {
        ConnectionPool.closeConnection(connection);
    }


}
