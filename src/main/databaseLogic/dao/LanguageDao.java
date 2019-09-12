package databaseLogic.dao;

public interface LanguageDao {
    int getLanguageId(String language);
    void closeConnection();

    String getLanguageById(int languageId);
}
