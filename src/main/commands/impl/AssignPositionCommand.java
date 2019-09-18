package commands.impl;
import commands.Command;
import commands.commandHelpers.AssignPositionHelper;
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
        String email = request.getParameter("email");
        String position = request.getParameter("userType");
        User currentUser = (User) request.getSession().getAttribute("user");


        AssignPositionHelper helper = new AssignPositionHelper(email, position, currentUser);
        String result = helper.handle();

        MessageManager message = new MessageManager();
        request.setAttribute(result, message.getProperty(result));
        request.setAttribute("userPosition", position);
        return ConfigManager.getProperty("cabinet");
    }
}
