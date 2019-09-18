package databaseLogic.dao.impl;

import databaseLogic.connection.ConnectionPool;
import databaseLogic.dao.LanguageDao;
import databaseLogic.dao.PositionDao;
import databaseLogic.dao.SpeakerDao;
import databaseLogic.factory.DaoFactory;
import entity.Speaker;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SpeakerDaoImpl implements SpeakerDao {

    private Connection connection;

    public SpeakerDaoImpl() {
        connection = ConnectionPool.getConnection();
    }

    public SpeakerDaoImpl(Connection connection) {
        this.connection = connection;
    }


    @Override
    public Speaker getSpeakerById(Long id) {
        Speaker speaker = null;
        PositionDao positionDao = DaoFactory.getPositionDao(connection);
        LanguageDao languageDao = DaoFactory.getLanguageDao(connection);
        try (PreparedStatement statement = connection.prepareStatement("SELECT id, name, surname, email, password, position,language,rating " +
                "FROM users u join speakerratings s on u.id=s.speakerId WHERE id=? AND position=?")) {
            statement.setLong(1, id);
            statement.setInt(2, positionDao.getPositionId("Speaker"));
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                speaker = new Speaker();
                speaker.setId(id);
                speaker.setName(rs.getString("name"));
                speaker.setSurname(rs.getString("surname"));
                speaker.setEmail(rs.getString("email"));
                speaker.setPassword("password");
                speaker.setPosition("Speaker");
                speaker.setLanguage(languageDao.getLanguageById(rs.getInt("language")));
                speaker.setRating(rs.getInt("rating"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return speaker;
    }

    @Override
    public Speaker getSpeakerByEmail(String email) {
        Speaker speaker = null;
        PositionDao positionDao = DaoFactory.getPositionDao(connection);
        LanguageDao languageDao = DaoFactory.getLanguageDao(connection);
        try (PreparedStatement statement = connection.prepareStatement("SELECT id,name,surname,password,language,rating " +
                "from users u join speakerratings s " +
                "on u.id = s.speakerId where position=? and email=?")) {
            int position = positionDao.getPositionId("Speaker");
            statement.setInt(1, position);
            statement.setString(2, email);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                speaker = new Speaker();
                speaker.setId(rs.getLong("id"));
                speaker.setName(rs.getString("name"));
                speaker.setSurname(rs.getString("surname"));
                speaker.setPassword(rs.getString("password"));
                speaker.setLanguage(languageDao.getLanguageById(rs.getInt("language")));
                speaker.setRating(rs.getInt("rating"));
                speaker.setEmail(email);
                speaker.setPosition("Speaker");
            }
        } catch (SQLException e) {
            e.printStackTrace();                                    //todo
        }
        return speaker;
    }

    @Override
    public void deleteSpeaker(Long speakerId) {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("DELETE from speakerratings where speakerId=?");
            statement.setLong(1, speakerId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();                                   //todo
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public int addSpeakerRating(Speaker speaker, int rating) {
        PreparedStatement statement = null;
        int result = 0;
        try {
            statement = connection.prepareStatement("UPDATE speakerratings SET rating=? WHERE speakerId=?");
            statement.setInt(1, rating);
            statement.setLong(2, speaker.getId());
            result = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null)
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }
        return result;
    }

    @Override
    public int addBonusesToSpeaker(Speaker speaker, int bonuses) {
        PreparedStatement statement = null;
        int result = 0;
        try {
            statement = connection.prepareStatement("UPDATE speakerratings " +
                    "set bonuses=? where speakerId=?");
            statement.setInt(1, getSpeakerBonuses(speaker) + bonuses);
            statement.setLong(2, speaker.getId());
            result = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null)
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }
        return result;
    }

    @Override
    public int getSpeakerBonuses(Speaker speaker) {
        PreparedStatement statement = null;
        int bonuses = 0;
        try {
            statement = connection.prepareStatement("SELECT bonuses " +
                    "FROM speakerratings where speakerId=?");
            statement.setLong(1, speaker.getId());
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                bonuses = rs.getInt("bonuses");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null)
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }
        return bonuses;
    }

    @Override
    public void addSpeaker(Long speakerId) {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("INSERT speakerratings(speakerId) values (?)");
            statement.setLong(1, speakerId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();                                 //todo
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    @Override
    public void closeConnection() {
        ConnectionPool.closeConnection(connection);
    }
}
