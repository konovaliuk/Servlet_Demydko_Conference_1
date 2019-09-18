package transaction;

import databaseLogic.connection.ConnectionPool;
import databaseLogic.dao.PositionDao;
import databaseLogic.dao.SpeakerDao;
import databaseLogic.dao.UserDao;
import databaseLogic.factory.DaoFactory;
import entity.User;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

public class UserTransaction {
    private Logger logger = Logger.getLogger(UserTransaction.class);


    public Long addUser(User user) {
        Connection connection = ConnectionPool.getConnection();
        UserDao userDao = DaoFactory.getUserDao(connection);
        PositionDao positionDao = DaoFactory.getPositionDao(connection);
        SpeakerDao speakerDao = DaoFactory.getSpeakerDao(connection);
        Long id = null;
        try {
            connection.setAutoCommit(false);
            id = userDao.addUser(user);
            user.setId(id);
            positionDao.setPositionForUser(user, user.getPosition());
            if (user.getPosition().equals("Speaker")) {
                speakerDao.addSpeaker(user.getId());
            }
            connection.commit();
        } catch (SQLException e) {
            logger.error(e);
            try {
                connection.rollback();
            } catch (SQLException ex) {
                logger.error(ex);
            }
        } finally {
            ConnectionPool.closeConnection(connection);
        }
        return id;
    }
}
