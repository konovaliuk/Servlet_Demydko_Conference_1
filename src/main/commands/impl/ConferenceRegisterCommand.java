package commands.impl;

import commands.Command;
import commands.commandHelpers.impl.ConferenceRegisterHelper;
import entity.Report;
import entity.User;
import servises.configManager.ConfigManager;
import servises.messageManager.MessageManager;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public class ConferenceRegisterCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) {

        List<Report> reportList = (List<Report>) request.getSession().getAttribute("reportList");
        User user = (User) request.getSession().getAttribute("user");
        String sIndex = request.getParameter("index");
        Map<Long, Integer> countOfVisitors = (Map<Long, Integer>) request.getSession().getAttribute("countOfVisitors");

        ConferenceRegisterHelper helper = new ConferenceRegisterHelper(reportList, user, sIndex, countOfVisitors);
        String result = helper.handle();

        MessageManager message = new MessageManager();
        if (result.equals("errorAlreadyRegistered")) {
            request.setAttribute("reportId", helper.getReportId());
            request.setAttribute(result, message.getProperty(result));

        }
        return ConfigManager.getProperty("futureReports");

    }
}
