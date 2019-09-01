package databaseLogic.dao.impl;

import databaseLogic.connection.DataSourceConference;
import databaseLogic.connection.TestDataSource;
import databaseLogic.dao.UserDao;
import entity.Speaker;
import entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoImpl implements UserDao {
    private DataSourceConference dataSource;                             // todo
    //  private TestDataSource dataSource;                             // todo

    private Connection connection;

    public UserDaoImpl() {
        dataSource = new DataSourceConference();
        //dataSource = new TestDataSource();
        this.connection = dataSource.getConnection();
    }

    @Override
    public int addUser(User user) {
        PreparedStatement statement = null;
        int result = 0;
        try {
            int position = getPositionId(user.getPosition());
            if (position != -1) {
                connection.setAutoCommit(false);
                statement = connection.prepareStatement("INSERT users(name, surname, email, password, position) values (?,?,?,?,?)");
                statement.setString(1, user.getName());
                statement.setString(2, user.getSurname());
                statement.setString(3, user.getEmail());
                statement.setString(4, user.getPassword());
                statement.setInt(5, position);
                statement.executeUpdate();
                if (user.getPosition().equals("Speaker")) {
                    statement = connection.prepareStatement("SELECT LAST_INSERT_ID()");
                    ResultSet rs = statement.executeQuery();
                    rs.next();
                    addSpeaker(rs.getInt(1));
                }
                connection.commit();
            } else {
                System.out.println("Position is incorrect");                //todo
            }
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();                                         //todo
            }
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    @Override
    public int getPositionId(String position) {
        PreparedStatement statement = null;
        int result = -1;
        try {
            statement = connection.prepareStatement("SELECT id from positions where position=?");
            statement.setString(1, position);
            ResultSet rs = statement.executeQuery();
            rs.next();
            result = rs.getInt("id");
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
    public String getPosition(int id) {
        PreparedStatement statement = null;
        String position = null;
        try {
            statement = connection.prepareStatement("SELECT position from positions where id=?");
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                position = rs.getString("position");
            } else {
                System.out.println("Position's id is incorrect");                                  // todo
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
        return position;
    }

    @Override
    public User getUserByEmail(String email) {
        PreparedStatement statement = null;
        User user = new User();
        try {
            statement = connection.prepareStatement("SELECT * from users where email=?");
            statement.setString(1, email);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setSurname(rs.getString("surname"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                int position = rs.getInt("position");
                user.setPosition(getPosition(position));
            } else {
                return null;                                               //todo
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
        return user;
    }

    @Override
    public User getUserById(long id) {
        PreparedStatement statement = null;
        User user = null;
        try {
            statement = connection.prepareStatement("SELECT * FROM users WHERE id=?");
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                user = new User();
                user.setId(id);
                user.setName(rs.getString("name"));
                user.setSurname(rs.getString("surname"));
                user.setEmail(rs.getString("email"));
                user.setPassword("password");
                user.setPosition(getPosition(rs.getInt("position")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null)
                    statement.close();
            } catch (SQLException e) {
                e.printStackTrace();                           //todo
            }
        }
        return user;
    }


    @Override
    public Speaker getSpeakerById(long id) {
        PreparedStatement statement = null;
        Speaker speaker = null;
        try {
            statement = connection.prepareStatement("SELECT id, name, surname, email, password, position,rating " +
                    "FROM users u join speakerratings s on u.id=s.speakerId WHERE id=? AND position=?");
            statement.setLong(1, id);
            statement.setInt(2, getPositionId("Speaker"));
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                speaker = new Speaker();
                speaker.setId(id);
                speaker.setName(rs.getString("name"));
                speaker.setSurname(rs.getString("surname"));
                speaker.setEmail(rs.getString("email"));
                speaker.setPassword("password");
                speaker.setPosition("Speaker");
                speaker.setRating(rs.getInt("rating"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null)
                    statement.close();
            } catch (SQLException e) {
                e.printStackTrace();                           //todo
            }
        }
        return speaker;
    }

    @Override
    public int setPosition(User user, String position) {
        PreparedStatement statement = null;
        int result = 0;
        try {
            connection.setAutoCommit(false);
            if (user.getPosition().equals("Speaker")) {
                deleteSpeaker(user.getId());
            }
            statement = connection.prepareStatement("UPDATE users set position=? where email=?");
            statement.setInt(1, getPositionId(position));
            statement.setString(2, user.getEmail());
            result = statement.executeUpdate();
            if (position.equals("Speaker")) {
                addSpeaker(user.getId());
            }
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();                                        //todo
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();                                    //todo
            }
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
    public Speaker getSpeakerByEmail(String email) {
        PreparedStatement statement = null;
        Speaker speaker = null;
        try {
            statement = connection.prepareStatement("SELECT id,name,surname,password,rating " +
                    "from users u join speakerratings s " +
                    "on u.id = s.speakerId where position=? and email=?");
            int position = getPositionId("Speaker");
            statement.setInt(1, position);
            statement.setString(2, email);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                speaker = new Speaker();
                speaker.setId(rs.getInt("id"));
                speaker.setName(rs.getString("name"));
                speaker.setSurname(rs.getString("surname"));
                speaker.setPassword(rs.getString("password"));
                speaker.setRating(rs.getInt("rating"));
                speaker.setEmail(email);
                speaker.setPosition("Speaker");
            }
        } catch (SQLException e) {
            e.printStackTrace();                                    //todo
        } finally {
            if (statement != null) {
                try {
                    statement.close();                              //todo
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return speaker;
    }

    @Override
    public void deleteSpeaker(long speakerId) {
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

    private void addSpeaker(long speakerId) {
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
    public void closeConnection() {
        dataSource.closeConnection();
    }


}
