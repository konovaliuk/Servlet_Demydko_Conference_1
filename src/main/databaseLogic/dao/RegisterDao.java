package databaseLogic.dao;

import entity.User;

import java.util.List;

public interface RegisterDao {

    int userRegister(long userId, long reportId);

    List<Long> getReportsIdByUserId(long userId);

    List<User> getAllRegisteredUsers(long reportId);

    int addPresence(long reportId, int presence);

//    boolean isReportPresent(long reportId);    //todo delete

    void closeConnection();
}
