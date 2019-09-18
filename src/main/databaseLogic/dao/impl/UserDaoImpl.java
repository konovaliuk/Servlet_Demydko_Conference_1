package databaseLogic.dao.impl;

import databaseLogic.connection.ConnectionPool;
import databaseLogic.dao.LanguageDao;
import databaseLogic.dao.PositionDao;
import databaseLogic.dao.SpeakerDao;
import databaseLogic.dao.UserDao;
import databaseLogic.factory.DaoFactory;
import entity.User;

import java.sql.*;

public class UserDaoImpl implements UserDao {


    private Connection connection;

    public UserDaoImpl() {
        connection = ConnectionPool.getConnection();
    }

    public UserDaoImpl(Connection connection) {
        this.connection = connection;
    }

//    @Override
//    public Long addUser(User user) {
//        Long id = null;
//        PositionDao positionDao = DaoFactory.getPositionDao(connection);
//        SpeakerDao speakerDao = DaoFactory.getSpeakerDao(connection);
//        LanguageDao languageDao = DaoFactory.getLanguageDao(connection);
//        try (PreparedStatement statement = connection.prepareStatement(
//                "INSERT users(name, surname, email, password, position, language)"
//                        + " values (?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS)) {
//            int position = positionDao.getPositionId(user.getPosition());
//            if (position != -1) {
//                connection.setAutoCommit(false);
//                statement.setString(1, user.getName());
//                statement.setString(2, user.getSurname());
//                statement.setString(3, user.getEmail());
//                statement.setString(4, user.getPassword());
//                statement.setInt(5, position);
//                statement.setInt(6, languageDao.getLanguageId(user.getLanguage()));
//                statement.executeUpdate();
//                ResultSet rs = statement.getGeneratedKeys();
//                rs.next();
//                id = rs.getLong(1);
//                if (user.getPosition().equals("Speaker")) {
//                    speakerDao.addSpeaker(id);
//                }
//                connection.commit();
//            } else {
//                System.out.println("Position is incorrect");                //todo
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            try {
//                connection.rollback();
//            } catch (SQLException ex) {
//                ex.printStackTrace();                                         //todo
//            }
//        }
//        return id;
//    }

//    @Override
////    public Long addUser(User user) {
////        Long id = null;
////        PositionDao positionDao = DaoFactory.getPositionDao(connection);
////        LanguageDao languageDao = DaoFactory.getLanguageDao(connection);
////        try (PreparedStatement statement = connection.prepareStatement(
////                "INSERT users(name, surname, email, password, position, language)"
////                        + " values (?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS)) {
////            int position = positionDao.getPositionId(user.getPosition());
////            if (position != -1) {
////                statement.setString(1, user.getName());
////                statement.setString(2, user.getSurname());
////                statement.setString(3, user.getEmail());
////                statement.setString(4, user.getPassword());
////                statement.setInt(5, position);
////                statement.setInt(6, languageDao.getLanguageId(user.getLanguage()));
////                statement.executeUpdate();
////                ResultSet rs = statement.getGeneratedKeys();
////                rs.next();
////                id = rs.getLong(1);
////            } else {
////                System.out.println("Position is incorrect");                //todo
////            }
////        } catch (SQLException e) {
////            e.printStackTrace();
////        }
////        return id;
////    }


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
            e.printStackTrace();
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
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public User getUserById(Long id) {
        User user = null;
        PositionDao positionDao = DaoFactory.getPositionDao(connection);
        LanguageDao languageDao = DaoFactory.getLanguageDao(connection);
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE id=?")){
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
        }
        return user;
    }

//    @Override
//    public int setPositionForUser(User user, String position) {
//        PreparedStatement statement = null;
//        int result = 0;
//        PositionDao positionDao = DaoFactory.getPositionDao();
//        SpeakerDao speakerDao = DaoFactory.getSpeakerDao(connection);
//        try {
//            connection.setAutoCommit(false);
//            if (user.getPosition().equals("Speaker")) {
//                speakerDao.deleteSpeaker(user.getId());
//            }
//            statement = connection.prepareStatement("UPDATE users set position=? where email=?");
//            statement.setInt(1, positionDao.getPositionId(position));
//            statement.setString(2, user.getEmail());
//            result = statement.executeUpdate();
//            if (position.equals("Speaker")) {
//                speakerDao.addSpeaker(user.getId());
//            }
//            connection.commit();
//        } catch (SQLException e) {
//            e.printStackTrace();                                        //todo
//            try {
//                connection.rollback();
//            } catch (SQLException ex) {
//                ex.printStackTrace();                                    //todo
//            }
//        } finally {
//            positionDao.closeConnection();
//            if (statement != null)
//                try {
//                    statement.close();
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//        }
//        return result;
//    }

    @Override
    public void closeConnection() {
        ConnectionPool.closeConnection(connection);
    }


}
