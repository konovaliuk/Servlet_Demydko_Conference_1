package databaseLogic.dao;

public interface PresenceDao {

    int addPresence(Long reportId, int presence);
    void closeConnection();
}
