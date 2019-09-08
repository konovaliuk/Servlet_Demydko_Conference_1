package databaseLogic.dao;

public interface PositionDao {
    String getPosition(int position);
    int getPositionId(String position);
    void closeConnection();

}
