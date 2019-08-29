package commands.impl;

import commands.Command;
import databaseLogic.dao.UserDao;
import databaseLogic.factory.DaoFactory;
import entity.User;
import servises.checkUserManager.CheckUserManager;
import servises.configManager.ConfigManager;
import servises.messageManager.MessageManager;

import javax.servlet.http.HttpServletRequest;

public class AssignModeratorCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        String page = ConfigManager.getProperty("cabinet");

        String email = request.getParameter("email");

        if (!CheckUserManager.isEmailCorrect(email)) {
            request.setAttribute("errorEmailForm", MessageManager.getProperty("emailForm"));
            return page;
        }

        UserDao userDao = DaoFactory.getUserDao();
        User user = userDao.getUserByEmail(email);


        if (user == null) {
            request.setAttribute("errorUserNotExists", MessageManager.getProperty("userNotExists"));
            return page;
        }

        int result = userDao.setPosition(user, "Moderator");
        userDao.closeConnection();

        if (result != 0) {
            request.setAttribute("successfulChanges", MessageManager.getProperty("successfulChanges"));
            return page;
        }

        return page;
    }
}
