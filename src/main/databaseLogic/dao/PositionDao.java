package databaseLogic.dao;

import entity.User;

public interface PositionDao {
    String getPosition(int position);
    int setPositionForUser(User user, String position);
    int getPositionId(String position);
    void closeConnection();

}
