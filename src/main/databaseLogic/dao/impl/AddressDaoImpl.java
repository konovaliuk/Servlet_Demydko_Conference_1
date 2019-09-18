package databaseLogic.dao.impl;

import databaseLogic.connection.ConnectionPool;
import databaseLogic.dao.AddressDao;
import entity.Address;

import java.sql.*;

public class AddressDaoImpl implements AddressDao {

    private Connection connection;

    public AddressDaoImpl() {
        connection = ConnectionPool.getConnection();
    }

    public AddressDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Long addAddress(String city, String street, String building, String room) {
        Long id = null;
        try (PreparedStatement statement =connection.prepareStatement("INSERT address" +
                "(city,street,building,room) values (?,?,?,?)", Statement.RETURN_GENERATED_KEYS)){
            statement.setString(1, city);
            statement.setString(2, street);
            statement.setString(3, building);
            statement.setString(4, room);
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            rs.next();
            id = rs.getLong(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    @Override
    public Long getAddressId(Address address) {
        PreparedStatement statement = null;
        Long id = null;
        try {
            statement = connection.prepareStatement("SELECT id FROM address " +
                    "where city=? and street=? and building=? and room=?");
            statement.setString(1, address.getCity());
            statement.setString(2, address.getStreet());
            statement.setString(3, address.getBuilding());
            statement.setString(4, address.getRoom());
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                id = rs.getLong(1);
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
        return id;
    }

    @Override
    public Address getAddressById(Long id) {
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
        ConnectionPool.closeConnection(connection);
    }
}
