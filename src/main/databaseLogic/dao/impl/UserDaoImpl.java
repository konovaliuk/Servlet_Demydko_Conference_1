package databaseLogic.dao.impl;

import databaseLogic.connection.DataSourceConference;
import databaseLogic.connection.TestDataSource;
import databaseLogic.dao.LanguageDao;
import databaseLogic.dao.PositionDao;
import databaseLogic.dao.SpeakerDao;
import databaseLogic.dao.UserDao;
import databaseLogic.factory.DaoFactory;
import entity.Speaker;
import entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoImpl implements UserDao {

    private DataSourceConference dataSource;                             // todo
    private Connection connection;
    //  private TestDataSource dataSource;                             // todo


    public UserDaoImpl() {
        dataSource = DataSourceConference.getInstance();
        this.connection = dataSource.getConnection();
        //dataSource = new TestDataSource();
    }

    @Override
    public int addUser(User user) {
        PreparedStatement statement = null;
        int result = 0;
        PositionDao positionDao = DaoFactory.getPositionDao();
        SpeakerDao speakerDao = DaoFactory.getSpeakerDao();
        LanguageDao languageDao = DaoFactory.getLanguageDao();
        try {
            int position = positionDao.getPositionId(user.getPosition());
            if (position != -1) {
                connection.setAutoCommit(false);
                statement = connection.prepareStatement("INSERT users(name, surname, email, password, position, language)" +
                        " values (?,?,?,?,?,?)");
                statement.setString(1, user.getName());
                statement.setString(2, user.getSurname());
                statement.setString(3, user.getEmail());
                statement.setString(4, user.getPassword());
                statement.setInt(5, position);
                statement.setInt(6, languageDao.getLanguageId(user.getLanguage()));
                statement.executeUpdate();
                if (user.getPosition().equals("Speaker")) {
                    statement = connection.prepareStatement("SELECT LAST_INSERT_ID()");
                    ResultSet rs = statement.executeQuery();
                    rs.next();
                    speakerDao.addSpeaker(rs.getLong(1));
                }
                connection.commit();
            } else {
                System.out.println("Position is incorrect");                //todo
            }
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();                                         //todo
            }
        } finally {
            speakerDao.closeConnection();
            positionDao.closeConnection();
            languageDao.closeConnection();
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    @Override
    public User getUserByEmail(String email) {
        PreparedStatement statement = null;
        User user = new User();
        PositionDao positionDao = DaoFactory.getPositionDao();
        LanguageDao languageDao = DaoFactory.getLanguageDao();
        try {
            statement = connection.prepareStatement("SELECT * from users where email=?");
            statement.setString(1, email);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                user.setId(rs.getLong("id"));
                user.setName(rs.getString("name"));
                user.setSurname(rs.getString("surname"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                int position = rs.getInt("position");
                user.setPosition(positionDao.getPosition(position));
                user.setLanguage(languageDao.getLanguageById(rs.getInt("language")));
            } else {
                return null;                                               //todo
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            positionDao.closeConnection();
            languageDao.closeConnection();
            if (statement != null)
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }
        return user;
    }

    @Override
    public User getUserById(Long id) {
        PreparedStatement statement = null;
        User user = null;
        PositionDao positionDao = DaoFactory.getPositionDao();
        LanguageDao languageDao = DaoFactory.getLanguageDao();
        try {
            statement = connection.prepareStatement("SELECT * FROM users WHERE id=?");
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
            e.printStackTrace();
        } finally {
            positionDao.closeConnection();
            languageDao.closeConnection();
            try {
                if (statement != null)
                    statement.close();
            } catch (SQLException e) {
                e.printStackTrace();                           //todo
            }
        }
        return user;
    }

    @Override
    public int setUserPosition(User user, String position) {
        PreparedStatement statement = null;
        int result = 0;
        PositionDao positionDao = DaoFactory.getPositionDao();
        SpeakerDao speakerDao = DaoFactory.getSpeakerDao(connection);
        try {
            connection.setAutoCommit(false);
            if (user.getPosition().equals("Speaker")) {
                speakerDao.deleteSpeaker(user.getId());
            }
            statement = connection.prepareStatement("UPDATE users set position=? where email=?");
            statement.setInt(1, positionDao.getPositionId(position));
            statement.setString(2, user.getEmail());
            result = statement.executeUpdate();
            if (position.equals("Speaker")) {
                speakerDao.addSpeaker(user.getId());
            }
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();                                        //todo
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();                                    //todo
            }
        } finally {
            positionDao.closeConnection();
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
    public void closeConnection() {
        dataSource.closeConnection();
    }


}
