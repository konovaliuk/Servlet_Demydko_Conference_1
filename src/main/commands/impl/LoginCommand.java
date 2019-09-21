package commands.impl;

import commands.Command;
import commands.commandHelpers.impl.LoginHelper;
import entity.User;

import servises.configManager.ConfigManager;
import servises.messageManager.MessageManager;

import javax.servlet.http.HttpServletRequest;

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

