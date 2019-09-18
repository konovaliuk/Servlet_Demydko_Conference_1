package databaseLogic.dao;

public interface LanguageDao {

    int getLanguageId(String language);
    String getLanguageById(int languageId);


    void closeConnection();
}
