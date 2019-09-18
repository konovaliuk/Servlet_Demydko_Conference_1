package commands.impl;

import commands.Command;
import commands.commandHelpers.LoginHelper;
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
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        LoginHelper helper = new LoginHelper(email, password);
        String result = helper.handle();

        MessageManager message = new MessageManager();
        if (!result.equals("success")) {
            request.setAttribute(result, message.getProperty(result));
            return ConfigManager.getProperty("login");
        }
        String language = helper.getLanguage();
        User user = helper.getUser();
        request.getSession().setAttribute("language", language);
        request.getSession().setAttribute("user", user);
        return ConfigManager.getProperty("cabinet");
    }
}

