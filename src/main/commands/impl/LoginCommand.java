package commands.impl;

import commands.Command;
import databaseLogic.dao.UserDao;
import databaseLogic.factory.DaoFactory;
import entity.User;

import servises.configManager.ConfigManager;
import servises.languageManager.LanguageManager;
import servises.messageManager.MessageManager;
import servises.parameterManager.ParameterManager;
import servises.userManager.UserManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LoginCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) {
        String page = ConfigManager.getProperty("login");

        String email = request.getParameter("email");
        String password = request.getParameter("password");
        ParameterManager pm = new ParameterManager();

        MessageManager message = new MessageManager();

        if (!pm.isEmailCorrect(email)) {
            request.setAttribute("errorEmailForm", message.getProperty("errorEmailForm"));
            return page;
        }

        if (!pm.isPasswordCorrect(password)) {
            request.setAttribute("errorPassword", message.getProperty("errorPassword"));
            return page;
        }

        UserManager userManager = new UserManager();
        User user = userManager.getUserByEmail(email);

//        UserDao userDao = DaoFactory.getUserDao();
//        User user = userDao.getUserByEmail(email);
//        userDao.closeConnection();

        if (user == null || !password.equals(user.getPassword())) {
            request.setAttribute("errorUserNotExists", message.getProperty("errorUserNotExists"));
            return page;
        }

        user.setPassword(null);
        HttpSession session = request.getSession();
        session.setAttribute("user", user);

        LanguageManager languageManager = new LanguageManager();
        String language = languageManager.setLanguageToSession(user.getLanguage());
        request.getSession().setAttribute("language", language);

        return ConfigManager.getProperty("cabinet");
    }
}

