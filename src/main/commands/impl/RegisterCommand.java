package commands.impl;

import commands.Command;
import commands.commandHelpers.RegisterHelper;
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
        MessageManager message = new MessageManager();

        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String position = request.getParameter("userType");
        String language = (String) request.getSession().getAttribute("language");

        RegisterHelper helper = new RegisterHelper(email, password, name, surname, position, language);
        String result = helper.handle();

        request.getSession().setAttribute("language", helper.getLanguage());
        request.getSession().setAttribute("user", helper.getUser());

        if (result.equals("success")) {
            return ConfigManager.getProperty("success");
        }
        request.setAttribute(result, message.getProperty(result));
        return ConfigManager.getProperty("register");
    }
}
