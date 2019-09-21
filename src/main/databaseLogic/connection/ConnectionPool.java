package databaseLogic.connection;

import org.apache.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionPool {
    private static Logger logger = Logger.getLogger(ConnectionPool.class);
    private static DataSource dataSource;
    private ConnectionPool() {
    }


    static {
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            dataSource = (DataSource) envContext.lookup("jdbc/ConferenceDb");
        } catch (NamingException e) {
            logger.error(e);
        }
    }

    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            logger.error(e);
        }
        return connection;
    }


    public static void closeConnection(Connection connection) {
        try {
            if (!connection.isClosed()) {
                connection.close();
            } else {
                logger.error("connection is closed");
            }
        } catch (SQLException e) {
            logger.error(e);
        }
    }
}
