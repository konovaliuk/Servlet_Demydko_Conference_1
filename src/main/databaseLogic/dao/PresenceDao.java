package databaseLogic.dao;

public interface PresenceDao {

    int addPresence(Long reportId, int presence);
    int getPresence(Long reportId);
    void closeConnection();
}
