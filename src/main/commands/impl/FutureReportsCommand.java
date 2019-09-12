package commands.impl;

import commands.Command;
import entity.Report;
import entity.User;
import org.apache.log4j.Logger;
import servises.configManager.ConfigManager;
import servises.paginationManager.PaginationManager;
import servises.registerManager.RegisterManager;
import servises.reportManager.ReportManager;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public class FutureReportsCommand implements Command {
    private Logger logger = Logger.getLogger(FutureReportsCommand.class);

    @Override
    public String execute(HttpServletRequest request) {

        String requestOffset = request.getParameter("offset");
        String requestMaxCount = request.getParameter("maxCount");

        ReportManager reportManager = new ReportManager();
        PaginationManager paginationManager = new PaginationManager();
        int countOfFutureReports = reportManager.getCountOfFutureReports();
        int offset;
        int maxCount;
        List<Report> futureConferenceList;
        List<Integer> buttons;

        if (requestOffset == null && requestMaxCount == null) {
            String sessionMaxCount = (String) request.getSession().getAttribute("maxCount");
            maxCount = (sessionMaxCount != null) ? Integer.parseInt(sessionMaxCount) : 5;

            Integer sessionOffset = (Integer) request.getSession().getAttribute("offset");
            offset = (sessionOffset != null) ? sessionOffset : 0;

            futureConferenceList = reportManager.getFutureConference(offset, maxCount);
            buttons = paginationManager.getButtons(countOfFutureReports, maxCount);
            request.getSession().setAttribute("buttons", buttons);
        } else {
            if (requestMaxCount == null) {
                String sessionMaxCount = (String) request.getSession().getAttribute("maxCount");
                if (sessionMaxCount == null) {
                    maxCount = 5;
                } else {
                    maxCount = Integer.parseInt(sessionMaxCount);
                }
            } else {
                maxCount = Integer.parseInt(requestMaxCount);
                request.getSession().setAttribute("maxCount", requestMaxCount);
                buttons = paginationManager.getButtons(countOfFutureReports, maxCount);
                request.getSession().setAttribute("buttons", buttons);
            }
            Integer sessionOffset = (Integer) request.getSession().getAttribute("offset");
            if (requestOffset != null) {
                offset = Integer.parseInt(requestOffset) * maxCount - maxCount;
            } else if (sessionOffset != null) {
                offset = sessionOffset;
            } else {
                offset = 0;
            }
            request.getSession().setAttribute("offset", offset);
            futureConferenceList = reportManager.getFutureConference(offset, maxCount);
        }

        RegisterManager registerManager = new RegisterManager();
        User user = (User) request.getSession().getAttribute("user");
        if (user.getPosition().equals("User")) {
            List<Long> registerList = registerManager.getReportsIdByUserId(user);
            registerManager.checkRegistrationForUser(futureConferenceList, registerList);
        }

        Map<Long, Integer> countOfVisitors = registerManager.getCountOfVisitors(futureConferenceList);
        request.getSession().setAttribute("countOfVisitors", countOfVisitors);
        request.getSession().setAttribute("reportList", futureConferenceList);


        return ConfigManager.getProperty("futureReports");
    }
}
