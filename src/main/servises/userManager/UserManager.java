package servises.userManager;



import databaseLogic.dao.UserDao;
import databaseLogic.factory.DaoFactory;
import entity.User;
import transaction.PositionTransaction;
import transaction.UserTransaction;

public class UserManager {
    UserDao userDao;

    public User getUserByEmail(String email) {
        userDao = DaoFactory.getUserDao();
        User user = userDao.getUserByEmail(email);
        userDao.closeConnection();
        return user;
    }

//    public int setUserPosition(User user, String position) {
//        PositionDao positionDao = DaoFactory.getPositionDao();
//        int result = positionDao.setPositionForUser(user, position);
//        positionDao.closeConnection();
//        return result;
//    }

    public int setUserPosition(User user, String position) {
        PositionTransaction transaction = new PositionTransaction();
        return transaction.setPositionForUser(user, position);
    }

//    public Long addUser(User user) {
//        userDao = DaoFactory.getUserDao();
//        Long id = userDao.addUser(user);
//        userDao.closeConnection();
//        return id;
//    }

    public Long addUser(User user) {
        UserTransaction transaction = new UserTransaction();
        return transaction.addUser(user);
    }
}
