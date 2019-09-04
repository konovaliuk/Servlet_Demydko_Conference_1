package databaseLogic.dao;

import entity.User;

import java.util.List;

public interface RegisterDao {

    int userRegister(Long userId, Long reportId);

    List<Long> getReportsIdByUserId(Long userId);

    List<User> getAllRegisteredUsers(Long reportId);

    int addPresence(Long reportId, int presence);

//    boolean isReportPresent(long reportId);    //todo delete

    void closeConnection();
}
