package servises.userManager;



import databaseLogic.dao.UserDao;
import databaseLogic.factory.DaoFactory;
import entity.User;
import transaction.PositionTransaction;
import transaction.UserTransaction;

/**
 * This class encapsulated some methods from {@link UserDao}
 */

public class UserManager {

    public User getUserByEmail(String email) {
        UserDao userDao = DaoFactory.getUserDao();
        User user = userDao.getUserByEmail(email);
        userDao.closeConnection();
        return user;
    }

    public int setUserPosition(User user, String position) {
        PositionTransaction transaction = new PositionTransaction();
        return transaction.setPositionForUser(user, position);
    }

    public Long addUser(User user) {
        UserTransaction transaction = new UserTransaction();
        return transaction.addUser(user);
    }
}
