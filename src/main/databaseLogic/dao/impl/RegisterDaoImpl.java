package databaseLogic.dao.impl;

import databaseLogic.connection.DataSourceConference;
import databaseLogic.connection.TestDataSource;
import databaseLogic.dao.RegisterDao;
import databaseLogic.dao.UserDao;
import databaseLogic.factory.DaoFactory;
import entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RegisterDaoImpl implements RegisterDao {

    private DataSourceConference dataSource;                             // todo
    private Connection connection;
    // private TestDataSource dataSource;

    public RegisterDaoImpl() {
        dataSource = DataSourceConference.getInstance();
        this.connection = dataSource.getConnection();
        // dataSource = new TestDataSource();
    }

    @Override
    public int userRegister(Long userId, Long reportId) {
        PreparedStatement statement = null;
        int result = 0;
        try {
            statement = connection.prepareStatement("INSERT registeredlist(reportId, userId) values (?,?)");
            statement.setLong(1, reportId);
            statement.setLong(2, userId);
            result = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();                         // todo
        } finally {
            if (statement != null)
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();                  // todo
                }
        }
        return result;
    }

    @Override
    public List<Long> getReportsIdByUserId(Long userId) {
        PreparedStatement statement = null;
        List<Long> list = new ArrayList<>();
        try {
            statement = connection.prepareStatement("SELECT reportId from registeredlist where userId =?");
            statement.setLong(1, userId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                long id = rs.getInt("reportId");
                list.add(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();                    //todo
        } finally {
            if (statement != null)
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();                      // todo
                }
        }
        return list;
    }

    @Override
    public List<User> getAllRegisteredUsers(Long reportId) {
        PreparedStatement statement = null;
        List<User> userList = new ArrayList<>();
        UserDao userDao = DaoFactory.getUserDao();
        try {
            statement = connection.prepareStatement("SELECT userId from registeredlist where reportId=?");
            statement.setLong(1, reportId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                long id = rs.getInt("userid");
                User user = userDao.getUserById(id);
                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            userDao.closeConnection();
            if (statement != null)
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }
        return userList;
    }

    @Override
    public int getCountOfVisitors(Long reportId) {
        PreparedStatement statement = null;
        int result = 0;
        try {
            statement = connection.prepareStatement("SELECT count(userId) as sum from registeredlist where reportId =? ");
            statement.setLong(1,reportId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                result = rs.getInt("sum");
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
    public void closeConnection() {
        dataSource.closeConnection();
    }
}
