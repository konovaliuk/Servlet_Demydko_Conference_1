package commands.impl;

import commands.Command;
import databaseLogic.dao.ReportDao;
import databaseLogic.dao.SpeakerDao;
import databaseLogic.factory.DaoFactory;
import entity.Speaker;
import entity.User;
import servises.configManager.ConfigManager;
import servises.messageManager.MessageManager;
import servises.parameterManager.ParameterManager;

import javax.servlet.http.HttpServletRequest;

public class OfferReportCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) {
        String page = ConfigManager.getProperty("cabinet");
        String theme = request.getParameter("theme");

        if (theme.isEmpty()) {
            request.setAttribute("noActionDone", MessageManager.getProperty("noAction"));
            return page;
        }
        ParameterManager pm = new ParameterManager();

        if (!pm.isThemeCorrect(theme)) {
            request.setAttribute("errorTheme", MessageManager.getProperty("themeIncorrect"));
            return page;
        }

        ReportDao reportDao = DaoFactory.getReportDao();

        SpeakerDao speakerDao = DaoFactory.getSpeakerDao();
        User user = (User) request.getSession().getAttribute("user");

        Speaker speaker = speakerDao.getSpeakerById(user.getId());
        int result = reportDao.addReport(theme, speaker);
        speakerDao.closeConnection();
        reportDao.closeConnection();

        if (result != 0) {
            request.setAttribute("successfulChanges", MessageManager.getProperty("successfulChanges"));
        }

        return page;
    }
}
