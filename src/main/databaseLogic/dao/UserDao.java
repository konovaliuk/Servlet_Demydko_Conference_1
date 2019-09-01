package databaseLogic.dao;

import entity.Speaker;
import entity.User;

public interface UserDao {

    int addUser(User user);

    int setPosition(User user, String position);

    int getPositionId(String position);

    String getPosition(int position);

    User getUserByEmail(String email);

    User getUserById(long id);

    Speaker getSpeakerByEmail(String email);

    Speaker getSpeakerById(long id);

    int addSpeakerRating(Speaker speaker, int rating);

    void deleteSpeaker(long speakerId);

    void closeConnection();
}
