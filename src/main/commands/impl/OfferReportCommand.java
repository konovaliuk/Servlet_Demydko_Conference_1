package commands.impl;

import commands.Command;
import commands.commandHelpers.OfferReportHelper;
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
        String theme = request.getParameter("theme");
        User user = (User) request.getSession().getAttribute("user");

        OfferReportHelper helper = new OfferReportHelper(theme,user);
        String result = helper.handle();

        MessageManager message = new MessageManager();
        request.setAttribute(result, message.getProperty(result));
        return ConfigManager.getProperty("cabinet");
    }
}
