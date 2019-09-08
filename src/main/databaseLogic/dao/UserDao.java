package databaseLogic.dao;

import entity.User;

public interface UserDao {

    int addUser(User user);

    int setUserPosition(User user, String position);

    User getUserByEmail(String email);

    User getUserById(Long id);

    void closeConnection();
}
