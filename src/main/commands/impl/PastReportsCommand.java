package commands.impl;

import commands.Command;
import commands.commandHelpers.PastReportsHelper;
import databaseLogic.dao.PresenceDao;
import databaseLogic.dao.ReportDao;
import databaseLogic.factory.DaoFactory;
import entity.Report;
import servises.configManager.ConfigManager;
import servises.paginationManager.PaginationManager;
import servises.presenceManager.PresenceManager;
import servises.reportManager.ReportManager;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PastReportsCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) {

        String requestOffset = request.getParameter("offsetPast");
        String requestMaxCount = request.getParameter("maxCountPast");
        Integer sessionOffset = (Integer) request.getSession().getAttribute("offsetPast");
        Integer sessionMaxCount = (Integer) request.getSession().getAttribute("maxCountPast");

        PastReportsHelper helper = new PastReportsHelper(requestOffset, requestMaxCount, sessionOffset, sessionMaxCount);
        helper.handle();

        request.getSession().setAttribute("pastReportList", helper.getPastConferenceList());
        request.getSession().setAttribute("maxCountPast", helper.getMaxCount());
        request.getSession().setAttribute("offsetPast", helper.getOffset());
        request.getSession().setAttribute("pastReportPresence", helper.getPastReportPresence());
        if (helper.getButtons() != null)
            request.getSession().setAttribute("buttonsPast", helper.getButtons());

        return ConfigManager.getProperty("pastReports");
    }

}

