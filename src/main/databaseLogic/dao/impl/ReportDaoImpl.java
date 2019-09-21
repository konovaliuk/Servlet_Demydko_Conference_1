package databaseLogic.dao.impl;

import databaseLogic.connection.ConnectionPool;
import databaseLogic.dao.AddressDao;
import databaseLogic.dao.ReportDao;
import databaseLogic.dao.SpeakerDao;
import databaseLogic.factory.DaoFactory;
import entity.Address;
import entity.Report;
import entity.Speaker;
import org.apache.log4j.Logger;
import servises.dateTimeManager.DateTimeManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReportDaoImpl implements ReportDao {
    private Logger logger = Logger.getLogger(ReportDaoImpl.class);
    private Connection connection;

    public ReportDaoImpl() {
        connection = ConnectionPool.getConnection();
    }

    public ReportDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Long addReport(Report report) {
        Long id = null;
        DateTimeManager dtm = new DateTimeManager();
        try (PreparedStatement statement = connection.prepareStatement("INSERT reports" +
                "(name, date, time, speakerId)values (?,?,?,?)", Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, report.getName());
            statement.setDate(2, dtm.fromUtilDateToSqlDate(report.getDate()));
            statement.setTime(3, report.getTime());
            statement.setLong(4, report.getSpeaker().getId());
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            rs.next();
            id = rs.getLong(1);
        } catch (SQLException e) {
            logger.error(e);
        }
        return id;
    }


    @Override
    public void setAddressForReport(Long addressId, Long reportId) {
        try (PreparedStatement statement = connection.prepareStatement("UPDATE reports " +
                "set addressId=? where id=?")) {
            statement.setLong(1, addressId);
            statement.setLong(2, reportId);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
        }
    }

    @Override
    public int addReport(String name, Speaker speaker) {
        int result = 0;
        try (PreparedStatement statement = connection.prepareStatement("INSERT reports (name,speakerId) value (?,?)")) {
            statement.setString(1, name);
            statement.setLong(2, speaker.getId());
            result = statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
        }
        return result;
    }

    @Override
    public List<Report> getFutureConference(int offset, int maxCount) {
        AddressDao addressDao = DaoFactory.getAddressDao(connection);
        SpeakerDao speakerDao = DaoFactory.getSpeakerDao(connection);
        List<Report> reports = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement("SELECT id,name,date,time,addressId,speakerId" +
                " from reports where date>? order by id limit ? OFFSET ?")) {
            statement.setDate(1, new Date(new java.util.Date().getTime()));
            statement.setInt(2, maxCount);
            statement.setInt(3, offset);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Report report = new Report();
                report.setId(rs.getLong("id"));
                report.setName(rs.getString("name"));
                report.setDate(rs.getDate("date"));
                report.setTime(rs.getTime("time"));
                Speaker speaker = speakerDao.getSpeakerById(rs.getLong("speakerId"));
                report.setSpeaker(speaker);
                Address address = addressDao.getAddressById(rs.getLong("addressId"));
                report.setAddress(address);
                reports.add(report);
            }
        } catch (SQLException e) {
            logger.error(e);
        }
        return reports;
    }

    @Override
    public List<Report> getPastConference(int offset, int maxCount) {
        AddressDao addressDao = DaoFactory.getAddressDao(connection);
        SpeakerDao speakerDao = DaoFactory.getSpeakerDao(connection);
        List<Report> reports = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement("SELECT id,name,date,time,addressId,speakerId" +
                " from reports where date<? order by id limit ? OFFSET ?")) {
            statement.setDate(1, new Date(new java.util.Date().getTime()));
            statement.setInt(2, maxCount);
            statement.setInt(3, offset);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Report report = new Report();
                report.setId(rs.getLong("id"));
                report.setName(rs.getString("name"));
                report.setDate(rs.getDate("date"));
                report.setTime(rs.getTime("time"));
                Speaker speaker = speakerDao.getSpeakerById(rs.getLong("speakerId"));
                report.setSpeaker(speaker);
                Address address = addressDao.getAddressById(rs.getLong("addressId"));
                report.setAddress(address);
                reports.add(report);
            }
        } catch (SQLException e) {
            logger.error(e);
        }
        return reports;
    }

    @Override
    public int getCountOfFutureReports() {
        int result = 0;
        try (PreparedStatement statement = connection.prepareStatement("SELECT count(*)as sum from reports where date>?")) {
            statement.setDate(1, new Date(new java.util.Date().getTime()));
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                result = rs.getInt("sum");
            }
        } catch (SQLException e) {
            logger.error(e);
        }
        return result;
    }

    @Override
    public int getCountOfPastReports() {
        int result = 0;
        try (PreparedStatement statement = connection.prepareStatement("SELECT count(*)as sum from reports where date<?")) {
            statement.setDate(1, new Date(new java.util.Date().getTime()));
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                result = rs.getInt("sum");
            }
        } catch (SQLException e) {
            logger.error(e);
        }
        return result;
    }

    @Override
    public List<Report> getOfferedConference(int offset, int maxCount) {
        List<Report> reports = new ArrayList<>();
        SpeakerDao speakerDao = DaoFactory.getSpeakerDao(connection);
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT id,name, speakerId from reports where date is null order by id limit ? OFFSET ?")) {
            statement.setInt(1, maxCount);
            statement.setInt(2, offset);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Report report = new Report();
                report.setId(rs.getLong("id"));
                report.setName(rs.getString("name"));
                Speaker speaker = speakerDao.getSpeakerById(rs.getLong("speakerId"));
                report.setSpeaker(speaker);
                reports.add(report);
            }
        } catch (SQLException e) {
            logger.error(e);
        }
        return reports;
    }

    @Override
    public int getCountOfOfferedReports() {
        int result = 0;
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery("SELECT count(*)as sum from reports where date is null");
            if (rs.next()) {
                result = rs.getInt("sum");
            }
        } catch (SQLException e) {
            logger.error(e);
        }
        return result;
    }

    @Override
    public int updateReport(Report report) {
        int result = 0;
        DateTimeManager dtm = new DateTimeManager();
        try (PreparedStatement statement = connection.prepareStatement("UPDATE reports " +
                "set name=?, date=?, time=?, speakerId=? where id=?")) {
            statement.setString(1, report.getName());
            statement.setDate(2, dtm.fromUtilDateToSqlDate(report.getDate()));
            statement.setTime(3, report.getTime());
            statement.setLong(4, report.getSpeaker().getId());
            statement.setLong(5, report.getId());
            result = statement.executeUpdate();

        } catch (SQLException e) {
            logger.error(e);
        }
        return result;
    }


    @Override
    public int deleteReport(Long reportId) {
        int result = 0;
        try (Statement statement = connection.createStatement()) {
            result = statement.executeUpdate("DELETE from reports where id=" + reportId);
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
