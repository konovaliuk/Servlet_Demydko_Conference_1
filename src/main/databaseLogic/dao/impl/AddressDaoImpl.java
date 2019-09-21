package databaseLogic.dao.impl;

import databaseLogic.connection.ConnectionPool;
import databaseLogic.dao.AddressDao;
import entity.Address;
import org.apache.log4j.Logger;

import java.sql.*;

public class AddressDaoImpl implements AddressDao {
    private Logger logger = Logger.getLogger(AddressDaoImpl.class);
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
        try (PreparedStatement statement = connection.prepareStatement("INSERT address" +
                "(city,street,building,room) values (?,?,?,?)", Statement.RETURN_GENERATED_KEYS)) {
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
            logger.error(e);
        }
        return id;
    }

    @Override
    public Long getAddressId(Address address) {

        Long id = null;
        try (PreparedStatement statement = connection.prepareStatement("SELECT id FROM address " +
                "where city=? and street=? and building=? and room=?")){
            statement.setString(1, address.getCity());
            statement.setString(2, address.getStreet());
            statement.setString(3, address.getBuilding());
            statement.setString(4, address.getRoom());
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                id = rs.getLong(1);
            }
        } catch (SQLException e) {
            logger.error(e);
        }
        return id;
    }

    @Override
    public Address getAddressById(Long id) {

        Address address = null;
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM address where id=?")){
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
            logger.error(e);
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
