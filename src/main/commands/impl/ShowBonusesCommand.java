package commands.impl;

import commands.Command;
import databaseLogic.dao.UserDao;
import databaseLogic.factory.DaoFactory;
import entity.Speaker;
import entity.User;
import servises.configManager.ConfigManager;
import servises.messageManager.MessageManager;

import javax.servlet.http.HttpServletRequest;

public class ShowBonusesCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        UserDao userDao = DaoFactory.getUserDao();
        Speaker speaker = userDao.getSpeakerById(user.getId());
        int bonuses = userDao.getSpeakerBonuses(speaker);
        userDao.closeConnection();
        request.setAttribute("bonuses", MessageManager.getProperty("bonuses") + bonuses);
        return ConfigManager.getProperty("cabinet");
    }

}
