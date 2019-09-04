package databaseLogic.dao;

import entity.Speaker;
import entity.User;

public interface UserDao {

    int addUser(User user);

    int setPosition(User user, String position);

    int getPositionId(String position);

    String getPosition(int position);

    User getUserByEmail(String email);

    User getUserById(Long id);

    Speaker getSpeakerByEmail(String email);

    Speaker getSpeakerById(Long id);

    int addSpeakerRating(Speaker speaker, int rating);

    int getSpeakerBonuses(Speaker speaker);

    int addBonusesToSpeaker(Speaker speaker, int bonuses);

    void deleteSpeaker(Long speakerId);

    void closeConnection();
}
