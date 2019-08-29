package databaseLogic.dao;

import entity.Address;

import java.sql.Connection;

public interface AddressDao {

    void addAddress(String city, String street, String building, String room);

    long getAddressId(Address address);

    Address getAddressById(long id);

    Connection getConnection();

    void closeConnection();
}
