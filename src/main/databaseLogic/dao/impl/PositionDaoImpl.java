package databaseLogic.dao.impl;

import databaseLogic.connection.ConnectionPool;
import databaseLogic.dao.PositionDao;
import databaseLogic.dao.SpeakerDao;
import databaseLogic.factory.DaoFactory;
import entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PositionDaoImpl implements PositionDao {

    private Connection connection;

    public PositionDaoImpl() {
        connection = ConnectionPool.getConnection();
    }

    public PositionDaoImpl(Connection connection) {
        this.connection = connection;
    }


    @Override
    public String getPosition(int id) {
        PreparedStatement statement = null;
        String position = null;
        try {
            statement = connection.prepareStatement("SELECT position from positions where id=?");
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                position = rs.getString("position");
            } else {
                System.out.println("Position's id is incorrect");                                  // todo
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
        return position;
    }

    @Override
    public int getPositionId(String position) {
        PreparedStatement statement = null;
        int result = -1;
        try {
            statement = connection.prepareStatement("SELECT id from positions where position=?");
            statement.setString(1, position);
            ResultSet rs = statement.executeQuery();
            rs.next();
            result = rs.getInt("id");
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
    public int setPositionForUser(User user, String position) {
        int result = 0;
        try (PreparedStatement statement = connection.prepareStatement("UPDATE users set position=? where email=?")) {
            statement.setInt(1, getPositionId(position));
            statement.setString(2, user.getEmail());
            result = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();                                        //todo
        }
        return result;
    }

    @Override
    public void closeConnection() {
        ConnectionPool.closeConnection(connection);
    }

}
