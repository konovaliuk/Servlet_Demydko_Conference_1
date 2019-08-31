package commands.impl;

import commands.Command;
import databaseLogic.dao.UserDao;
import databaseLogic.factory.DaoFactory;
import entity.User;
import servises.checkUserManager.CheckUserManager;
import servises.configManager.ConfigManager;
import servises.messageManager.MessageManager;
import servises.parameterManager.ParameterManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class RegisterCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        String page = ConfigManager.getProperty("register");

        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String position = request.getParameter("userType");

        if (ParameterManager.isAllEmpty(name, surname, position)) {
            request.setAttribute("errorEmptyForm", MessageManager.getProperty("emptyForm"));
            return page;
        }
        if (!CheckUserManager.isEmailCorrect(email)) {
            request.setAttribute("errorEmailForm", MessageManager.getProperty("emailForm"));
            return page;
        }
        if (!CheckUserManager.isPasswordCorrect(password)) {
            request.setAttribute("errorPassword", MessageManager.getProperty("passwordForm"));
            return page;
        }
        if (CheckUserManager.isUserExist(email)) {
            request.setAttribute("errorUserExists", MessageManager.getProperty("userExists"));
            return page;
        }

        User user = new User(name, surname, email, password, position);

        UserDao userDao = DaoFactory.getUserDao();
        userDao.addUser(user);
        userDao.closeConnection();

        HttpSession session = request.getSession();
        session.setAttribute("user", user);

        return ConfigManager.getProperty("success");
    }
}
