package databaseLogic.dao.impl;

import databaseLogic.connection.ConnectionPool;
import databaseLogic.dao.PositionDao;
import databaseLogic.dao.SpeakerDao;
import databaseLogic.factory.DaoFactory;
import entity.User;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PositionDaoImpl implements PositionDao {
    private Logger logger = Logger.getLogger(PositionDaoImpl.class);
    private Connection connection;

    public PositionDaoImpl() {
        connection = ConnectionPool.getConnection();
    }

    public PositionDaoImpl(Connection connection) {
        this.connection = connection;
    }


    @Override
    public String getPosition(int id) {
        String position = null;
        try(PreparedStatement statement =connection.prepareStatement("SELECT position from positions where id=?")){
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                position = rs.getString("position");
            }
        } catch (SQLException e) {
            logger.error(e);
        }
        return position;
    }

    @Override
    public int getPositionId(String position) {

        int result = -1;
        try(PreparedStatement statement = connection.prepareStatement("SELECT id from positions where position=?")) {
            statement.setString(1, position);
            ResultSet rs = statement.executeQuery();
            rs.next();
            result = rs.getInt("id");
        } catch (SQLException e) {
            logger.error(e);
        }
        return result;
    }

    @Override
    public int setPositionForUser(User user, String position) {
        int result = 0;
        try (PreparedStatement statement = connection.prepareStatement("UPDATE users set position=? where email=?")) {
            statement.setInt(1, getPositionId(position));
            statement.setString(2, user.getEmail());
            result = statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
        }
        return result;
    }

    @Override
    public void closeConnection() {
        ConnectionPool.closeConnection(connection);
    }

}
