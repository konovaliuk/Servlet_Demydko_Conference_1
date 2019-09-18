package commands.impl;

import commands.Command;
import commands.commandHelpers.FutureReportsHelper;
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
        Integer sessionMaxCount = (Integer) request.getSession().getAttribute("maxCount");
        Integer sessionOffset = (Integer) request.getSession().getAttribute("offset");
        User user = (User) request.getSession().getAttribute("user");


        FutureReportsHelper helper = new FutureReportsHelper(requestOffset, requestMaxCount, sessionOffset, sessionMaxCount, user);
        helper.handle();

        request.getSession().setAttribute("reportList", helper.getFutureConferenceList());
        request.getSession().setAttribute("offset", helper.getOffset());
        request.getSession().setAttribute("maxCount", helper.getMaxCount());
        request.getSession().setAttribute("countOfVisitors", helper.getCountOfVisitors());
        if (helper.getButtons() != null) {
            request.getSession().setAttribute("buttons", helper.getButtons());
        }
        return ConfigManager.getProperty("futureReports");
    }
}
