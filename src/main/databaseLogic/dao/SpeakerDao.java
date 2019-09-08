package databaseLogic.dao;

import entity.Speaker;

public interface SpeakerDao {

   void addSpeaker(Long speakerId);

    Speaker getSpeakerByEmail(String email);

    Speaker getSpeakerById(Long id);

    int addSpeakerRating(Speaker speaker, int rating);

    int getSpeakerBonuses(Speaker speaker);

    int addBonusesToSpeaker(Speaker speaker, int bonuses);

    void deleteSpeaker(Long speakerId);

    void closeConnection();
}
