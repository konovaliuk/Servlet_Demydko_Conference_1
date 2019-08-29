package databaseLogic.factory;

import databaseLogic.dao.AddressDao;
import databaseLogic.dao.ReportDao;
import databaseLogic.dao.UserDao;
import databaseLogic.dao.impl.AddressDaoImpl;
import databaseLogic.dao.impl.ReportDaoImpl;
import databaseLogic.dao.impl.UserDaoImpl;

import java.sql.Connection;

public class DaoFactory {

    public static UserDao getUserDao() {
        return new UserDaoImpl();
    }

    public static AddressDao getAddressDao() {
        return new AddressDaoImpl();
    }

    public static AddressDao getAddressDao(Connection connection) {
        return new AddressDaoImpl(connection);
    }

    public static ReportDao getReportDao() {
        return new ReportDaoImpl();
    }
}
