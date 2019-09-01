package servises.parameterManager;

import databaseLogic.dao.UserDao;
import databaseLogic.factory.DaoFactory;
import entity.Address;
import entity.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParameterManager {

    public static boolean isAllEmpty(String... parameters) {
        int count = 0;
        for (String p : parameters) {
            if (p == null || p.isEmpty())
                count++;
        }
        if (count == parameters.length) {
            return true;
        }
        return false;
    }

    public static boolean isEmpty(String... parameters) {
        for (String p : parameters) {
            if (p == null || p.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    public static boolean isUserExist(String email) {
        UserDao userDao = DaoFactory.getUserDao();
        User user = userDao.getUserByEmail(email);
        userDao.closeConnection();
        return user != null;
    }

    public static boolean isEmailCorrect(String email) {
        Pattern p = Pattern.compile("([\\w%+-]+)@(\\w+\\.)(\\w+)(\\.\\w+)?");
        Matcher m = p.matcher(email);
        return m.matches();
    }

    public static boolean isPasswordCorrect(String password) {
        Pattern p = Pattern.compile("[а-яА-Яa-zA-ZЇїЄєІі[0-9]]{5,}");
        Matcher m = p.matcher(password);
        return m.matches();
    }

    public static boolean isAddressCorrect(Address address) {
        int count = 0;
        Pattern pattern;
        Matcher matcher;
        if (!address.getCity().isEmpty()) {
            pattern = Pattern.compile("[а-яА-Яa-zA-ZЇїЄєІі]{2,30}");
            matcher = pattern.matcher(address.getCity());
            if (matcher.matches())
                count++;
        } else {
            count++;
        }
        if (!address.getStreet().isEmpty()) {
            pattern = Pattern.compile("[а-яА-Яa-zA-ZЇїЄєІі\\-\\s]{2,50}");
            matcher = pattern.matcher(address.getStreet());
            if (matcher.matches())
                count++;
        } else {
            count++;
        }
        if (!address.getBuilding().isEmpty()) {
            pattern = Pattern.compile("[а-яА-Яa-zA-ZЇїЄєІі0-9\\s/-]{1,10}");
            matcher = pattern.matcher(address.getBuilding());
            if (matcher.matches())
                count++;
        } else {
            count++;
        }
        if (!address.getRoom().isEmpty()) {
            pattern = Pattern.compile("[а-яА-Яa-zA-ZЇїЄєІі0-9]{1,5}");
            matcher = pattern.matcher(address.getRoom());
            if (matcher.matches())
                count++;
        } else {
            count++;
        }
        return count == 4;
    }

    public static boolean isNameAndSurnameCorrect(String name, String surname) {
        Pattern p = Pattern.compile("[а-яА-Яa-zA-ZЇїЄєІі-]{1,50}");
        return p.matcher(name).matches() && p.matcher(surname).matches();
    }

    public static boolean isThemeCorrect(String theme) {
        Pattern p = Pattern.compile("([^\\s]{1})((.){0,254})");
        return p.matcher(theme).matches();
    }
}

