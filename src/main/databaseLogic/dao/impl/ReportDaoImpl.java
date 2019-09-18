package databaseLogic.dao.impl;

import databaseLogic.connection.ConnectionPool;
import databaseLogic.dao.AddressDao;
import databaseLogic.dao.ReportDao;
import databaseLogic.dao.SpeakerDao;
import databaseLogic.factory.DaoFactory;
import entity.Address;
import entity.Report;
import entity.Speaker;
import servises.dateTimeManager.DateTimeManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReportDaoImpl implements ReportDao {


    private Connection connection;

    public ReportDaoImpl() {
        connection = ConnectionPool.getConnection();
    }

    public ReportDaoImpl(Connection connection) {
        this.connection = connection;
    }

//    @Override
//    public Long addReport(Report report) {
//        Long id = null;
//        DateTimeManager dtm = new DateTimeManager();
//        AddressDao addressDao = DaoFactory.getAddressDao(connection);
//        try (PreparedStatement statement = connection.prepareStatement("INSERT reports" +
//                "(name, addressId, date, time, speakerId)values (?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);){
//            connection.setAutoCommit(false);
//            Long addressId = addressDao.getAddressId(report.getAddress());
//            if (addressId == null) {
//                addressId = addressDao.addAddress(report.getAddress().getCity(),
//                        report.getAddress().getStreet(), report.getAddress()
//                                .getBuilding(), report.getAddress().getRoom());
//            }
//            statement.setString(1, report.getName());
//            statement.setLong(2, addressId);
//            statement.setDate(3, dtm.fromUtilDateToSqlDate(report.getDate()));
//            statement.setTime(4, report.getTime());
//            statement.setLong(5, report.getSpeaker().getId());
//            statement.executeUpdate();
//            ResultSet rs = statement.getGeneratedKeys();
//            rs.next();
//            id = rs.getLong(1);
//            connection.commit();
//        } catch (SQLException e) {
//            e.printStackTrace();                                    //todo
//            try {
//                connection.rollback();
//            } catch (SQLException ex) {
//                ex.printStackTrace();                              // todo
//            }
//        }
//        return id;
//    }


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
            e.printStackTrace();                                    //todo
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
            e.printStackTrace();
        }
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
    public List<Report> getFutureConference(int offset, int maxCount) {
        PreparedStatement statement = null;
        AddressDao addressDao = DaoFactory.getAddressDao(connection);
        SpeakerDao speakerDao = DaoFactory.getSpeakerDao(connection);
        List<Report> reports = new ArrayList<>();
        try {
            statement = connection.prepareStatement("SELECT id,name,date,time,addressId,speakerId" +
                    " from reports where date>? order by id limit ? OFFSET ?");
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
            e.printStackTrace();
        } finally {
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
    public List<Report> getPastConference(int offset, int maxCount) {
        PreparedStatement statement = null;
        AddressDao addressDao = DaoFactory.getAddressDao(connection);
        SpeakerDao speakerDao = DaoFactory.getSpeakerDao(connection);
        List<Report> reports = new ArrayList<>();
        try {
            statement = connection.prepareStatement("SELECT id,name,date,time,addressId,speakerId" +
                    " from reports where date<? order by id limit ? OFFSET ?");
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
            e.printStackTrace();
        } finally {
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
    public int getCountOfFutureReports() {
        PreparedStatement statement = null;
        int result = 0;
        try {
            statement = connection.prepareStatement("SELECT count(*)as sum from reports where date>?");
            statement.setDate(1, new Date(new java.util.Date().getTime()));
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
    public int getCountOfPastReports() {
        PreparedStatement statement = null;
        int result = 0;
        try {
            statement = connection.prepareStatement("SELECT count(*)as sum from reports where date<?");
            statement.setDate(1, new Date(new java.util.Date().getTime()));
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
    public List<Report> getOfferedConference() {
        Statement statement = null;
        List<Report> reports = new ArrayList<>();
        SpeakerDao speakerDao = DaoFactory.getSpeakerDao(connection);
        try {
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT id,name,speakerId from reports where date is null");
            while (rs.next()) {
                Report report = new Report();
                report.setId(rs.getLong("id"));
                report.setName(rs.getString("name"));
                Speaker speaker = speakerDao.getSpeakerById(rs.getLong("speakerId"));
                report.setSpeaker(speaker);
                reports.add(report);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
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
    public List<Report> getPastReports() {
        PreparedStatement statement = null;
        SpeakerDao speakerDao = DaoFactory.getSpeakerDao(connection);
        AddressDao addressDao = DaoFactory.getAddressDao(connection);
        List<Report> reports = new ArrayList<>();
        try {
            statement = connection.prepareStatement("SELECT " +
                    "id, name, date, time, addressId, speakerId from reports  where date<?");
            statement.setDate(1, new Date(new java.util.Date().getTime()));
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
            e.printStackTrace();
        } finally {
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
//    public int updateReport(Report report) {
//        int result = 0;
//        DateTimeManager dtm = new DateTimeManager();
//        AddressDao addressDao = DaoFactory.getAddressDao(connection);
//        try(PreparedStatement statement = connection.prepareStatement("UPDATE reports " +
//                "set name=?, addressId=?, date=?, time=?, speakerId=? where id=?");) {
//            connection.setAutoCommit(false);
//            Long addressId = addressDao.getAddressId(report.getAddress());
//            if (addressId == null) {
//              addressId=  addressDao.addAddress(report.getAddress().getCity(),
//                        report.getAddress().getStreet(), report.getAddress().getBuilding(),
//                        report.getAddress().getRoom());
//            }
//            statement.setString(1, report.getName());
//            statement.setLong(2, addressId);
//            statement.setDate(3, dtm.fromUtilDateToSqlDate(report.getDate()));
//            statement.setTime(4, report.getTime());
//            statement.setLong(5, report.getSpeaker().getId());
//            statement.setLong(6, report.getId());
//            result = statement.executeUpdate();
//            connection.commit();
//        } catch (SQLException e) {
//            e.printStackTrace();                                    //todo
//            try {
//                connection.rollback();
//            } catch (SQLException ex) {
//                ex.printStackTrace();                              // todo
//            }
//        }
//        return result;
//    }

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
            e.printStackTrace();                                    //todo
        }
        return result;
    }


    @Override
    public int deleteReport(Long reportId) {
        Statement statement = null;
        int result = 0;
        try {
            statement = connection.createStatement();
            result = statement.executeUpdate("DELETE from reports where id=" + reportId);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
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
        ConnectionPool.closeConnection(connection);
    }
}
