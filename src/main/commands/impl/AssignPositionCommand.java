package commands.impl;
import commands.Command;
import databaseLogic.dao.UserDao;
import databaseLogic.factory.DaoFactory;
import entity.User;
import servises.configManager.ConfigManager;
import servises.mailManager.MailManager;
import servises.messageManager.MessageManager;
import servises.parameterManager.ParameterManager;
import servises.userManager.UserManager;

import javax.servlet.http.HttpServletRequest;

public class AssignPositionCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        String page = ConfigManager.getProperty("cabinet");

        String email = request.getParameter("email");
        String position = request.getParameter("userType");

        ParameterManager parameterManager = new ParameterManager();
        MessageManager message = new MessageManager();

        if (!parameterManager.isEmailCorrect(email)) {
            request.setAttribute("errorEmailForm", message.getProperty("errorEmailForm"));
            return page;
        }

        UserManager userManager = new UserManager();
        User user = userManager.getUserByEmail(email);

        if (user == null) {
            request.setAttribute("errorUserNotExists", message.getProperty("errorUserNotExists"));
            return page;
        }

        if (user.getPosition().equals(position)) {
            request.setAttribute("errorPosition", message.getProperty("errorPosition")+
                    " " + position);
            return page;
        }

        int result = userManager.setUserPosition(user, position);
        MailManager mail = new MailManager();

        if (result != 0) {
            user.setPosition(position);
            mail.assignment(user);
            request.setAttribute("successfulChanges", message.getProperty("successfulChanges"));
        }

        return page;
    }
}
