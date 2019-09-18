package transaction;

import databaseLogic.connection.ConnectionPool;
import databaseLogic.dao.PositionDao;
import databaseLogic.dao.SpeakerDao;
import databaseLogic.factory.DaoFactory;
import entity.User;

import java.sql.Connection;
import java.sql.SQLException;

public class PositionTransaction {
    public int setPositionForUser(User user, String position) {
        int result = 0;
        if (user.getPosition().equals(position)) {
            return result;
        }
        Connection connection = ConnectionPool.getConnection();
        SpeakerDao speakerDao = DaoFactory.getSpeakerDao(connection);
        PositionDao positionDao = DaoFactory.getPositionDao(connection);
        try {
            connection.setAutoCommit(false);
            if (user.getPosition().equals("Speaker")) {
                speakerDao.deleteSpeaker(user.getId());
            }
            result = positionDao.setPositionForUser(user, position);
            if (position.equals("Speaker")) {
                speakerDao.addSpeaker(user.getId());
            }
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }finally {
            ConnectionPool.closeConnection(connection);
        }
        return result;
    }
}
