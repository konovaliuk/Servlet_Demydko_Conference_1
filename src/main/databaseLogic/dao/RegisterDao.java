package databaseLogic.dao;

import entity.User;

import java.util.List;

public interface RegisterDao {

    int userRegister(Long userId, Long reportId);

    List<Long> getReportsIdByUserId(Long userId);

    List<User> getAllRegisteredUsers(Long reportId);

    void closeConnection();
}
