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
import servises.reportManager.ReportManager;
import servises.spaekerManager.SpeakerManager;

import javax.servlet.http.HttpServletRequest;

public class OfferReportCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) {
        String page = ConfigManager.getProperty("cabinet");
        String theme = request.getParameter("theme");
        MessageManager message = new MessageManager();
        if (theme.isEmpty()) {
            request.setAttribute("noActionDone", message.getProperty("noActionDone"));
            return page;
        }
        ParameterManager parameterManager = new ParameterManager();
        if (!parameterManager.isThemeCorrect(theme)) {
            request.setAttribute("errorTheme", message.getProperty("errorTheme"));
            return page;
        }
        User user = (User) request.getSession().getAttribute("user");
        SpeakerManager speakerManager = new SpeakerManager();
        Speaker speaker = speakerManager.getSpeakerById(user.getId());
        ReportManager reportManager = new ReportManager();
        int result = reportManager.addReport(theme, speaker);
        if (result != 0) {
            request.setAttribute("successfulChanges", message.getProperty("successfulChanges"));
        }
        return page;
    }
}
