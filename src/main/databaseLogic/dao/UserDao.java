package databaseLogic.dao;

import entity.User;

public interface UserDao {

    Long addUser(User user);

//    int setPositionForUser(User user, String position);

    User getUserByEmail(String email);

    User getUserById(Long id);

    void closeConnection();
}
