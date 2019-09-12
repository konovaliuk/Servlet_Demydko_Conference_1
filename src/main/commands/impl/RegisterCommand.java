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

public class RegisterCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        String page = ConfigManager.getProperty("register");

        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String position = request.getParameter("userType");

        ParameterManager pm = new ParameterManager();
        MessageManager message = new MessageManager();

        if (pm.isEmpty(name, surname, position)) {
            request.setAttribute("errorEmptyForm", message.getProperty("errorEmptyForm"));
            return page;
        }
        if (!pm.isEmailCorrect(email)) {
            request.setAttribute("errorEmailForm", message.getProperty("errorEmailForm"));
            return page;
        }
        if (!pm.isPasswordCorrect(password)) {
            request.setAttribute("errorPassword", message.getProperty("errorPassword"));
            return page;
        }

        if (!pm.isNameAndSurnameCorrect(name, surname)) {
            request.setAttribute("errorNameOrSurname", message.getProperty("errorNameOrSurname"));
            return page;
        }

        if (pm.isUserExist(email)) {
            request.setAttribute("errorUserExists", message.getProperty("errorUserExists"));
            return page;
        }
        String language = (String) request.getSession().getAttribute("language");
        LanguageManager languageManager = new LanguageManager();
        language = languageManager.setLanguageToUser(language);
        User user = new User(name, surname, email, password, position, language);

        language = languageManager.setLanguageToSession(language);
        request.getSession().setAttribute("language", language);

        UserManager userManager = new UserManager();
        userManager.addUser(user);
        user = userManager.getUserByEmail(email);

//        UserDao userDao = DaoFactory.getUserDao();
//        userDao.addUser(user);
//        user = userDao.getUserByEmail(email);
//        userDao.closeConnection();


        user.setPassword(null);
        HttpSession session = request.getSession();
        session.setAttribute("user", user);

        return ConfigManager.getProperty("success");
    }
}
