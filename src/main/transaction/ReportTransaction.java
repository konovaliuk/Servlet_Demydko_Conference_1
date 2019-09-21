package transaction;

import databaseLogic.connection.ConnectionPool;
import databaseLogic.dao.AddressDao;
import databaseLogic.dao.ReportDao;
import databaseLogic.factory.DaoFactory;
import entity.Report;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * This class is used for transaction.
 */
public class ReportTransaction {
    private Logger logger = Logger.getLogger(ReportTransaction.class);

    public int addReport(Report report) {
        Connection connection = ConnectionPool.getConnection();
        ReportDao reportDao = DaoFactory.getReportDao(connection);
        AddressDao addressDao = DaoFactory.getAddressDao(connection);
        int result = 0;
        try {
            connection.setAutoCommit(false);
            Long reportId = reportDao.addReport(report);
            Long addressId = addressDao.getAddressId(report.getAddress());
            if (addressId == null) {
                String city = report.getAddress().getCity();
                String street = report.getAddress().getStreet();
                String building = report.getAddress().getBuilding();
                String room = report.getAddress().getRoom();
                addressId = addressDao.addAddress(city, street, building, room);
                reportDao.setAddressForReport(addressId, reportId);
            } else {
                reportDao.setAddressForReport(addressId, reportId);
            }
            connection.commit();
            result++;
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
        return result;
    }

    public int updateReport(Report report) {
        int result = 0;
        Connection connection = ConnectionPool.getConnection();
        ReportDao reportDao = DaoFactory.getReportDao(connection);
        AddressDao addressDao = DaoFactory.getAddressDao(connection);
        try {
            connection.setAutoCommit(false);
            Long addressId = addressDao.getAddressId(report.getAddress());
            if (addressId == null) {
                String city = report.getAddress().getCity();
                String street = report.getAddress().getStreet();
                String building = report.getAddress().getBuilding();
                String room = report.getAddress().getRoom();
                addressId = addressDao.addAddress(city, street, building, room);
                reportDao.updateReport(report);
                reportDao.setAddressForReport(addressId, report.getId());
            } else {
                reportDao.updateReport(report);
            }
            connection.commit();
            result++;
        } catch (SQLException e) {
            logger.error(e);
            try {
                connection.rollback();
            } catch (SQLException ex) {
                logger.error(ex);
            }
        }

        return result;
    }
}
