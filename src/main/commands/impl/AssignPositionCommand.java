package commands.impl;
import commands.Command;
import databaseLogic.dao.UserDao;
import databaseLogic.factory.DaoFactory;
import entity.User;
import servises.configManager.ConfigManager;
import servises.mailManager.MailManager;
import servises.messageManager.MessageManager;
import servises.parameterManager.ParameterManager;

import javax.servlet.http.HttpServletRequest;

public class AssignPositionCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        String page = ConfigManager.getProperty("cabinet");

        String email = request.getParameter("email");
        String position = request.getParameter("userType");

        ParameterManager pm = new ParameterManager();

        if (!pm.isEmailCorrect(email)) {
            request.setAttribute("errorEmailForm", MessageManager.getProperty("emailForm"));
            return page;
        }

        UserDao userDao = DaoFactory.getUserDao();
        User user = userDao.getUserByEmail(email);

        if (user == null) {
            request.setAttribute("errorUserNotExists", MessageManager.getProperty("userNotExists"));
            return page;
        }

        if (user.getPosition().equals(position)) {
            request.setAttribute("errorPosition", MessageManager.getProperty("userPosition")+
                    " " + position);
            return page;
        }

        int result = userDao.setUserPosition(user, position);
        userDao.closeConnection();

        if (result != 0) {
            user.setPosition(position);
            MailManager.assignment(user);
            request.setAttribute("successfulChanges", MessageManager.getProperty("successfulChanges"));
        }

        return page;
    }
}
