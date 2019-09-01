package commands.impl;

import commands.Command;
import databaseLogic.dao.UserDao;
import databaseLogic.factory.DaoFactory;
import entity.Speaker;
import servises.configManager.ConfigManager;
import servises.messageManager.MessageManager;
import servises.parameterManager.ParameterManager;

import javax.servlet.http.HttpServletRequest;

public class AddSpeakerRatingCommand implements Command {


    @Override
    public String execute(HttpServletRequest request) {
        String page = ConfigManager.getProperty("cabinet");

        String rating = request.getParameter("rating");
        String email = request.getParameter("email");

        if (!ParameterManager.isEmailCorrect(email)) {
            request.setAttribute("errorEmailForm", MessageManager.getProperty("emailForm"));
            return page;
        }

        UserDao userDao = DaoFactory.getUserDao();
        Speaker speaker = userDao.getSpeakerByEmail(email);


        if (speaker == null) {
            userDao.closeConnection();
            request.setAttribute("errorSpeakerNotExists", MessageManager.getProperty("speakerNotExists"));
            return page;
        }

        int result = userDao.addSpeakerRating(speaker, Integer.parseInt(rating));
        userDao.closeConnection();

        if (result != 0) {
            request.setAttribute("successfulChanges", MessageManager.getProperty("successfulChanges"));
        }

        return page;
    }
}
