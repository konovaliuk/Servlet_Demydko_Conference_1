package databaseLogic.dao.impl;

import databaseLogic.connection.DataSourceConference;
import databaseLogic.connection.TestDataSource;
import databaseLogic.dao.AddressDao;
import databaseLogic.dao.ReportDao;
import databaseLogic.dao.UserDao;
import databaseLogic.factory.DaoFactory;
import entity.Address;
import entity.Report;
import entity.Speaker;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReportDaoImpl implements ReportDao {

    private DataSourceConference dataSource;                             // todo
    //  private TestDataSource dataSource;                             // todo
    private Connection connection;

    public ReportDaoImpl() {
        dataSource = new DataSourceConference();
        //  dataSource = new TestDataSource();
        this.connection = dataSource.getConnection();
    }

    @Override
    public int addReport(String name, Address address, Date date, Time time, Speaker speaker) {
        PreparedStatement statement = null;
        int result = 0;
        AddressDao addressDao = DaoFactory.getAddressDao(connection);
        try {
            connection.setAutoCommit(false);
            long addressId = addressDao.getAddressId(address);
            if (addressId == -1) {
                addressDao.addAddress(address.getCity(),
                        address.getStreet(), address.getBuilding(), address.getRoom());
                statement = connection.prepareStatement("SELECT LAST_INSERT_ID()");
                ResultSet rs = statement.executeQuery();
                rs.next();
                addressId = rs.getInt(1);
            }
            statement = connection.prepareStatement("INSERT reports" +
                    "(name, addressId, date, time, speakerId)values (?,?,?,?,?)");
            statement.setString(1, name);
            statement.setLong(2, addressId);
            statement.setDate(3, date);
            statement.setTime(4, time);
            statement.setLong(5, speaker.getId());
            result = statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();                                    //todo
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();                              // todo
            }
        } finally {
            if (statement != null)
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();                                //todo
                }
        }
        return result;
    }

    @Override
    public List<Report> getFutureConference() {
        PreparedStatement statement = null;
        UserDao userDao = DaoFactory.getUserDao();
        AddressDao addressDao = DaoFactory.getAddressDao();
        List<Report> reports = new ArrayList<>();
        try {
            statement = connection.prepareStatement("SELECT id,name,date,time,addressId,speakerId from reports where date>?");
            statement.setDate(1, new Date(new java.util.Date().getTime()));
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Report report = new Report();
                report.setId(rs.getInt("id"));
                report.setName(rs.getString("name"));
                report.setDate(rs.getDate("date"));
                report.setTime(rs.getTime("time"));
                Speaker speaker = userDao.getSpeakerById(rs.getInt("speakerId"));
                report.setSpeaker(speaker);
                Address address = addressDao.getAddressById(rs.getInt("addressId"));
                report.setAddress(address);
                reports.add(report);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            userDao.closeConnection();
            addressDao.closeConnection();
            if (statement != null)
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }
        return reports;
    }

//    @Override
//    public int updateReport(long reportId, String name, Date date, Time time) {
//        PreparedStatement statement = null;
//        int result = 0;
//        try {
//            statement = connection.prepareStatement("UPDATE reports set name=?, date=?, time=? where id=?");
//            statement.setString(1, name);
//            statement.setDate(2, date);
//            statement.setTime(3, time);
//            statement.setLong(4, reportId);
//            result = statement.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();                      //todo
//        } finally {
//            try {
//                if (statement != null)
//                    statement.close();
//            } catch (SQLException e) {
//                e.printStackTrace();                       //todo
//            }
//        }
//        return result;
//    }

    @Override
    public int updateReport(long reportId, String name, Address address, Date date, Time time, Speaker speaker) {
        PreparedStatement statement = null;
        int result = 0;
        AddressDao addressDao = DaoFactory.getAddressDao(connection);
        try {
            connection.setAutoCommit(false);
            long addressId = addressDao.getAddressId(address);
            if (addressId == -1) {
                addressDao.addAddress(address.getCity(),
                        address.getStreet(), address.getBuilding(), address.getRoom());
                statement = connection.prepareStatement("SELECT LAST_INSERT_ID()");
                ResultSet rs = statement.executeQuery();
                rs.next();
                addressId = rs.getInt(1);
            }
            statement = connection.prepareStatement("UPDATE reports set name=?, addressId=?, date=?, time=?, speakerId=? where id=?");
            statement.setString(1, name);
            statement.setLong(2, addressId);
            statement.setDate(3, date);
            statement.setTime(4, time);
            statement.setLong(5, speaker.getId());
            statement.setLong(6, reportId);
            result = statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();                                    //todo
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();                              // todo
            }
        } finally {
            if (statement != null)
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();                                //todo
                }
        }
        return result;
    }

    @Override
    public void closeConnection() {
        dataSource.closeConnection();
    }
}
