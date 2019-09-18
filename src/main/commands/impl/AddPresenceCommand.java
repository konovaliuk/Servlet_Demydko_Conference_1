package commands.impl;

import commands.Command;
import commands.commandHelpers.AddPresenceHelper;
import entity.Report;
import servises.configManager.ConfigManager;
import servises.messageManager.MessageManager;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public class AddPresenceCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {

        String index = request.getParameter("index");
        String count = request.getParameter("presence");
        List<Report> pastReportList = (List<Report>) request.getSession().getAttribute("pastReportList");
        Map<Long, Integer> pastReportPresence = (Map<Long, Integer>) request.getSession().getAttribute("pastReportPresence");

        AddPresenceHelper helper = new AddPresenceHelper(pastReportList, pastReportPresence, index, count);
        String result = helper.handle();

        MessageManager message = new MessageManager();
        request.setAttribute(result, message.getProperty(result));
        return ConfigManager.getProperty("pastReports");
    }
}
