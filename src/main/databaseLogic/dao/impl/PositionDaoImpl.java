package databaseLogic.dao.impl;

import databaseLogic.connection.DataSourceConference;
import databaseLogic.dao.PositionDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PositionDaoImpl implements PositionDao {

    private DataSourceConference dataSource;                             // todo
    //  private TestDataSource dataSource;                             // todo

    private Connection connection;

    public PositionDaoImpl() {
        dataSource = new DataSourceConference();
        //dataSource = new TestDataSource();
        this.connection = dataSource.getConnection();
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

    @Override
    public void closeConnection() {
        dataSource.closeConnection();
    }

}
