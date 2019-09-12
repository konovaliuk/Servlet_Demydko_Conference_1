package servises.userManager;


import databaseLogic.dao.UserDao;
import databaseLogic.factory.DaoFactory;
import entity.User;

public class UserManager {
    UserDao userDao;

    public User getUserByEmail(String email) {
        userDao = DaoFactory.getUserDao();
        User user = userDao.getUserByEmail(email);
        userDao.closeConnection();
        return user;
    }

    public int setUserPosition(User user, String position) {
        userDao = DaoFactory.getUserDao();
        int result = userDao.setUserPosition(user, position);
        userDao.closeConnection();
        return result;
    }

    public void addUser(User user) {
        userDao = DaoFactory.getUserDao();
        userDao.addUser(user);
        userDao.closeConnection();
    }
}
