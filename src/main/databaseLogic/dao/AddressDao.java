package databaseLogic.dao;

import entity.Address;

import java.sql.Connection;

public interface AddressDao {

    Long addAddress(String city, String street, String building, String room);

    Long getAddressId(Address address);

    Address getAddressById(Long id);

    Connection getConnection();      //todo delete this

    void closeConnection();
}
