package commands.impl;

import commands.Command;
import commands.commandHelpers.impl.FutureReportsHelper;
import entity.User;
import org.apache.log4j.Logger;
import servises.configManager.ConfigManager;

import javax.servlet.http.HttpServletRequest;

public class FutureReportsCommand implements Command {
    private Logger logger = Logger.getLogger(FutureReportsCommand.class);

    @Override
    public String execute(HttpServletRequest request) {

        String requestButton = request.getParameter("button");
        String requestMaxCount = request.getParameter("maxCount");
        Integer sessionMaxCount = (Integer) request.getSession().getAttribute("maxCount");
        Integer sessionButton = (Integer) request.getSession().getAttribute("futureButton");
        Integer sessionOffset = (Integer) request.getSession().getAttribute("offset");
        User user = (User) request.getSession().getAttribute("user");


        FutureReportsHelper helper = new FutureReportsHelper(requestButton, sessionButton, requestMaxCount, sessionOffset, sessionMaxCount, user);
        helper.handle();

        request.getSession().setAttribute("reportList", helper.getFutureConferenceList());
        request.getSession().setAttribute("offset", helper.getOffset());
        request.getSession().setAttribute("futureButton", helper.getCurrentButton());
        request.getSession().setAttribute("maxCount", helper.getMaxCount());
        request.getSession().setAttribute("countOfVisitors", helper.getCountOfVisitors());
        request.getSession().setAttribute("buttons", helper.getButtons());

        return ConfigManager.getProperty("futureReports");
    }
}
