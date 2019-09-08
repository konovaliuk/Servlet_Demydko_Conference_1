package commands.impl;

import commands.Command;
import databaseLogic.dao.SpeakerDao;
import databaseLogic.factory.DaoFactory;
import entity.Speaker;
import servises.configManager.ConfigManager;
import servises.messageManager.MessageManager;
import servises.parameterManager.ParameterManager;
import servises.userManager.UserManager;

import javax.servlet.http.HttpServletRequest;

public class AddBonusesCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) {
        String page = ConfigManager.getProperty("cabinet");

        String bonuses = request.getParameter("bonuses");
        String email = request.getParameter("email");

        ParameterManager pm = new ParameterManager();

        if (!pm.isNumberCorrect(bonuses)) {
            request.setAttribute("errorNumber", MessageManager.getProperty("numberIncorrect"));
            return page;
        }

        if (!pm.isEmailCorrect(email)) {
            request.setAttribute("errorEmailForm", MessageManager.getProperty("emailForm"));
            return page;
        }

//        UserDao userDao = DaoFactory.getUserDao();
//        Speaker speaker = userDao.getSpeakerByEmail(email);

        SpeakerDao speakerDao = DaoFactory.getSpeakerDao();
        Speaker speaker = speakerDao.getSpeakerByEmail(email);


        if (speaker == null) {
            speakerDao.closeConnection();
            request.setAttribute("errorSpeakerNotExists", MessageManager.getProperty("speakerNotExists"));
            return page;
        }

        int allBonuses = UserManager.setSpeakerBonuses(Integer.parseInt(bonuses), speaker);

        int result = speakerDao.addBonusesToSpeaker(speaker, allBonuses);
        speakerDao.closeConnection();

        if (result != 0) {
            request.setAttribute("successfulChanges", MessageManager.getProperty("successfulChanges"));
        }

        return page;
    }
}
