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
import servises.dateTimeManager.DateTimeManager;

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
    public int addReport(Report report) {
        PreparedStatement statement = null;
        int result = 0;
        AddressDao addressDao = DaoFactory.getAddressDao(connection);
        try {
            connection.setAutoCommit(false);
            long addressId = addressDao.getAddressId(report.getAddress());
            if (addressId == -1) {
                addressDao.addAddress(report.getAddress().getCity(),
                        report.getAddress().getStreet(), report.getAddress()
                                .getBuilding(), report.getAddress().getRoom());
                statement = connection.prepareStatement("SELECT LAST_INSERT_ID()");
                ResultSet rs = statement.executeQuery();
                rs.next();
                addressId = rs.getInt(1);
            }
            statement = connection.prepareStatement("INSERT reports" +
                    "(name, addressId, date, time, speakerId)values (?,?,?,?,?)");
            statement.setString(1, report.getName());
            statement.setLong(2, addressId);
            statement.setDate(3, DateTimeManager.fromUtilDateToSqlDate(report.getDate()));
            statement.setTime(4, report.getTime());
            statement.setLong(5, report.getSpeaker().getId());
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
    public int addReport(String name, Speaker speaker) {
        PreparedStatement statement = null;
        int result = 0;
        try {
            statement = connection.prepareStatement("INSERT reports (name,speakerId) value (?,?)");
            statement.setString(1, name);
            statement.setLong(2, speaker.getId());
            result = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();                                   //todo
        } finally {
            if (statement != null)
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();                                 //todo
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

    @Override
    public List<Report> getOfferedConference() {
        Statement statement = null;
        List<Report> reports = new ArrayList<>();
        UserDao userDao = DaoFactory.getUserDao();
        try {
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT id,name,speakerId from reports where date is null");
            while (rs.next()) {
                Report report = new Report();
                report.setId(rs.getInt("id"));
                report.setName(rs.getString("name"));
                Speaker speaker = userDao.getSpeakerById(rs.getInt("speakerId"));
                report.setSpeaker(speaker);
                reports.add(report);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            userDao.closeConnection();
            if (statement != null)
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();                              //todo
                }
        }
        return reports;
    }

    @Override
    public int updateReport(Report report) {
        PreparedStatement statement = null;
        int result = 0;
        AddressDao addressDao = DaoFactory.getAddressDao(connection);
        try {
            connection.setAutoCommit(false);
            long addressId = addressDao.getAddressId(report.getAddress());
            if (addressId == -1) {
                addressDao.addAddress(report.getAddress().getCity(),
                        report.getAddress().getStreet(), report.getAddress().getBuilding(),
                        report.getAddress().getRoom());

                statement = connection.prepareStatement("SELECT LAST_INSERT_ID()");
                ResultSet rs = statement.executeQuery();
                rs.next();
                addressId = rs.getInt(1);
            }
            statement = connection.prepareStatement("UPDATE reports set name=?, addressId=?, date=?, time=?, speakerId=? where id=?");
            statement.setString(1, report.getName());
            statement.setLong(2, addressId);
            statement.setDate(3, DateTimeManager.fromUtilDateToSqlDate(report.getDate()));
            statement.setTime(4, report.getTime());
            statement.setLong(5, report.getSpeaker().getId());
            statement.setLong(6, report.getId());
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
    public int deleteReport(long reportId) {
        Statement statement = null;
        int result = 0;
        try {
            statement = connection.createStatement();
            result = statement.executeUpdate("DELETE from reports where id=" + reportId);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if(statement!=null) {
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
    public void closeConnection() {
        dataSource.closeConnection();
    }
}
