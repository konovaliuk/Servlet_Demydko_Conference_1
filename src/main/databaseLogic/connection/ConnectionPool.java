package databaseLogic.connection;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionPool {
    private static DataSource dataSource;

//    private static volatile ConnectionPool instance;
    //    private Connection connection;
    private ConnectionPool() {
    }


    static {
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            dataSource = (DataSource) envContext.lookup("jdbc/ConferenceDb");
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

//    public static ConnectionPool getInstance() {
//        if (instance == null) {
//            synchronized (ConnectionPool.class) {
//                if (instance == null) {
//                    instance = new ConnectionPool();
//                }
//            }
//        }
//        return instance;
//    }


    public static Connection getConnection() {
//        Connection connection = null;
//        try {
//            Context initContext = new InitialContext();
//            Context envContext = (Context) initContext.lookup("java:/comp/env");
//            DataSource dataSource = (javax.sql.DataSource) envContext.lookup("jdbc/ConferenceDb");
//            connection = dataSource.getConnection();
//        } catch (SQLException | NamingException e) {
//            e.printStackTrace();
//        }
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }


    public static void closeConnection(Connection connection) {
        try {
            if (!connection.isClosed()) {
                connection.close();
            } else {
                System.err.println("connection is closed");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
