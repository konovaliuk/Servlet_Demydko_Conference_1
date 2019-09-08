package commands.impl;

import commands.Command;
import databaseLogic.dao.UserDao;
import databaseLogic.factory.DaoFactory;
import entity.User;

import servises.configManager.ConfigManager;
import servises.messageManager.MessageManager;
import servises.parameterManager.ParameterManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LoginCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) {
        String page = ConfigManager.getProperty("login");

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        ParameterManager pm = new ParameterManager();

        if (!pm.isEmailCorrect(email)) {
            request.setAttribute("errorEmailForm", MessageManager.getProperty("emailForm"));
            return page;
        }

        if (!pm.isPasswordCorrect(password)) {
            request.setAttribute("errorPassword", MessageManager.getProperty("passwordForm"));
            return page;
        }

        UserDao userDao = DaoFactory.getUserDao();
        User user = userDao.getUserByEmail(email);
        userDao.closeConnection();

        if (user == null || !password.equals(user.getPassword())) {
            request.setAttribute("errorUserNotExists", MessageManager.getProperty("userNotExists"));
            return page;
        }

        HttpSession session = request.getSession();
        session.setAttribute("user", user);

        return ConfigManager.getProperty("cabinet");
    }
}

