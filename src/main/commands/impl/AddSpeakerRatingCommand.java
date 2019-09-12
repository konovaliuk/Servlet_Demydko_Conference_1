package commands.impl;

import commands.Command;
import databaseLogic.dao.SpeakerDao;
import databaseLogic.factory.DaoFactory;
import entity.Speaker;
import servises.configManager.ConfigManager;
import servises.messageManager.MessageManager;
import servises.parameterManager.ParameterManager;
import servises.spaekerManager.SpeakerManager;

import javax.servlet.http.HttpServletRequest;

public class AddSpeakerRatingCommand implements Command {


    @Override
    public String execute(HttpServletRequest request) {
        String page = ConfigManager.getProperty("cabinet");

        String rating = request.getParameter("rating");
        String email = request.getParameter("email");

        ParameterManager parameterManager = new ParameterManager();
        MessageManager message = new MessageManager();

        if (!parameterManager.isEmailCorrect(email)) {
            request.setAttribute("errorEmailForm", message.getProperty("errorEmailForm"));
            return page;
        }

        SpeakerManager speakerManager = new SpeakerManager();
        Speaker speaker = speakerManager.getSpeakerByEmail(email);

//        SpeakerDao speakerDao = DaoFactory.getSpeakerDao();
//        Speaker speaker = speakerDao.getSpeakerByEmail(email);



        if (speaker == null) {
//            speakerDao.closeConnection();
            request.setAttribute("errorSpeakerNotExists", message.getProperty("errorSpeakerNotExists"));
            return page;
        }

        int result=speakerManager.addSpeakerRating(speaker, Integer.parseInt(rating));

//        int result = speakerDao.addSpeakerRating(speaker, Integer.parseInt(rating));
//        speakerDao.closeConnection();

        if (result != 0) {
            request.setAttribute("successfulChanges", message.getProperty("successfulChanges"));
        }

        return page;
    }
}
