package commands.impl;

import commands.Command;
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

        ReportManager reportManager = new ReportManager();
        PaginationManager paginationManager = new PaginationManager();
        int countOfPastReports = reportManager.getCountOfPastReports();
        int offset;
        int maxCount;
        List<Report> pastReportsList;
        List<Integer> buttons;

        if (requestOffset == null && requestMaxCount == null) {
            String sessionMaxCount = (String) request.getSession().getAttribute("maxCountPast");
            maxCount = (sessionMaxCount != null) ? Integer.parseInt(sessionMaxCount) : 5;
            Integer sessionOffset = (Integer) request.getSession().getAttribute("offsetPast");
            offset = (sessionOffset != null) ? sessionOffset : 0;
            pastReportsList = reportManager.getPastConference(offset, maxCount);
            buttons = paginationManager.getButtons(countOfPastReports, maxCount);
            request.getSession().setAttribute("buttonsPast", buttons);
        } else {
            if (requestMaxCount == null) {
                String sessionMaxCount = (String) request.getSession().getAttribute("maxCountPast");
                if (sessionMaxCount == null) {
                    maxCount = 5;
                } else {
                    maxCount = Integer.parseInt(sessionMaxCount);
                }
            } else {
                maxCount = Integer.parseInt(requestMaxCount);
                request.getSession().setAttribute("maxCountPast", requestMaxCount);
                buttons = paginationManager.getButtons(countOfPastReports, maxCount);
                request.getSession().setAttribute("buttonsPast", buttons);
            }
            Integer sessionOffset = (Integer) request.getSession().getAttribute("offsetPast");
            if (requestOffset != null) {
                offset = Integer.parseInt(requestOffset) * maxCount - maxCount;
            } else if (sessionOffset != null) {
                offset = sessionOffset;
            } else {
                offset = 0;
            }
            request.getSession().setAttribute("offsetPast", offset);
            pastReportsList = reportManager.getPastConference(offset, maxCount);
        }

        request.getSession().setAttribute("pastReportList", pastReportsList);
        PresenceManager presenceManager = new PresenceManager();
        Map<Long, Integer> pastReportPresence = presenceManager.getPresence(pastReportsList);
        request.getSession().setAttribute("pastReportPresence", pastReportPresence);

        return ConfigManager.getProperty("pastReports");
    }

}

