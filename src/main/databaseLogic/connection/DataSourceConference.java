package databaseLogic.connection;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSourceConference {

    private Connection connection;

    public Connection getConnection() {
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            javax.sql.DataSource dataSource = (javax.sql.DataSource) envContext.lookup("jdbc/EPAMdb");
            connection = dataSource.getConnection();
        } catch (SQLException | NamingException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public void closeConnection() {
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
