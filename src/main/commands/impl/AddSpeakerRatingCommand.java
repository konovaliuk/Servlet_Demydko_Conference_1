package commands.impl;

import commands.Command;
import databaseLogic.dao.SpeakerDao;
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

        ParameterManager pm = new ParameterManager();

        if (!pm.isEmailCorrect(email)) {
            request.setAttribute("errorEmailForm", MessageManager.getProperty("emailForm"));
            return page;
        }
        SpeakerDao speakerDao = DaoFactory.getSpeakerDao();
        Speaker speaker = speakerDao.getSpeakerByEmail(email);



        if (speaker == null) {
            speakerDao.closeConnection();
            request.setAttribute("errorSpeakerNotExists", MessageManager.getProperty("speakerNotExists"));
            return page;
        }

        int result = speakerDao.addSpeakerRating(speaker, Integer.parseInt(rating));
        speakerDao.closeConnection();

        if (result != 0) {
            request.setAttribute("successfulChanges", MessageManager.getProperty("successfulChanges"));
        }

        return page;
    }
}
