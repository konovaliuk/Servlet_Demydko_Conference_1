package databaseLogic.dao.impl;

import databaseLogic.connection.DataSourceConference;
import databaseLogic.connection.TestDataSource;
import databaseLogic.dao.AddressDao;
import entity.Address;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddressDaoImpl implements AddressDao {
    private DataSourceConference dataSource;                             // todo
    // private TestDataSource dataSource;
    private Connection connection;

    public AddressDaoImpl() {
        dataSource = new DataSourceConference();
        //   dataSource = new TestDataSource();
        this.connection = dataSource.getConnection();
    }

    public AddressDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void addAddress(String city, String street, String building, String room) {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("INSERT address(city,street,building,room) values (?,?,?,?)");
            statement.setString(1, city);
            statement.setString(2, street);
            statement.setString(3, building);
            statement.setString(4, room);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null)
                    statement.close();
            } catch (SQLException e) {
                e.printStackTrace();                           //todo
            }
        }

    }

    @Override
    public long getAddressId(Address address) {
        PreparedStatement statement = null;
        long id = -1;
        try {
            statement = connection.prepareStatement("SELECT id FROM address " +
                    "where city=? and street=? and building=? and room=?");
            statement.setString(1, address.getCity());
            statement.setString(2, address.getStreet());
            statement.setString(3, address.getBuilding());
            statement.setString(4, address.getRoom());
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    @Override
    public Address getAddressById(long id) {
        PreparedStatement statement = null;
        Address address = null;
        try {
            statement = connection.prepareStatement("SELECT * FROM address where id=?");
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                address = new Address();
                address.setId(id);
                address.setCity(rs.getString("city"));
                address.setStreet(rs.getString("street"));
                address.setBuilding(rs.getString("building"));
                address.setRoom(rs.getString("room"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null)
                    statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return address;
    }

    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    public void closeConnection() {
        dataSource.closeConnection();
    }
}
