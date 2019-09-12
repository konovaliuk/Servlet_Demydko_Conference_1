package commands.impl;

import commands.Command;
import databaseLogic.dao.SpeakerDao;
import databaseLogic.factory.DaoFactory;
import entity.Speaker;
import servises.configManager.ConfigManager;
import servises.mailManager.MailManager;
import servises.messageManager.MessageManager;
import servises.parameterManager.ParameterManager;
import servises.spaekerManager.SpeakerManager;
import servises.userManager.UserManager;

import javax.servlet.http.HttpServletRequest;

public class AddBonusesCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) {
        String page = ConfigManager.getProperty("cabinet");

        String bonuses = request.getParameter("bonuses");
        String email = request.getParameter("email");

        ParameterManager pm = new ParameterManager();
        MessageManager message = new MessageManager();

        if (!pm.isNumberCorrect(bonuses)) {
            request.setAttribute("errorNameOrSurname", message.getProperty("errorNumber"));
            return page;
        }

        if (!pm.isEmailCorrect(email)) {
            request.setAttribute("errorEmailForm",message.getProperty("errorEmailForm"));
            return page;
        }

        SpeakerManager speakerManager = new SpeakerManager();
        Speaker speaker=speakerManager.getSpeakerByEmail(email);

//        SpeakerDao speakerDao = DaoFactory.getSpeakerDao();
//        Speaker speaker = speakerDao.getSpeakerByEmail(email);


        if (speaker == null) {
//            speakerDao.closeConnection();
            request.setAttribute("errorSpeakerNotExists", message.getProperty("errorSpeakerNotExists"));
            return page;
        }

        int allBonuses = speakerManager.setSpeakerBonuses(Integer.parseInt(bonuses), speaker);

        int result = speakerManager.addBonusesToSpeaker(speaker, allBonuses);

//        int result = speakerDao.addBonusesToSpeaker(speaker, allBonuses);
//        speakerDao.closeConnection();

        if (result != 0) {
            request.setAttribute("successfulChanges", message.getProperty("successfulChanges"));
        }

        return page;
    }
}
