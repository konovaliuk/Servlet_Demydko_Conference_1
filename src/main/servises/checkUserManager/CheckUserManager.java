package servises.checkUserManager;

import databaseLogic.dao.UserDao;
import databaseLogic.factory.DaoFactory;
import entity.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckUserManager {
    public static boolean isUserExist(String email) {
        UserDao userDao = DaoFactory.getUserDao();
        User user = userDao.getUserByEmail(email);
        userDao.closeConnection();
        return user != null;
    }

    public static boolean isEmailCorrect(String email) {
        Pattern p = Pattern.compile("(\\w+)@(\\w+\\.)(\\w+)(\\.\\w+)?");
        Matcher m = p.matcher(email);
        return m.matches();
    }

    public static boolean isPasswordCorrect(String password) {
        Pattern p = Pattern.compile("[а-яА-Яa-zA-ZЇїЄєІі[0-9]]{5,}");
        Matcher m = p.matcher(password);
        return m.matches();
    }
}
