package commands.impl;

import commands.Command;
import databaseLogic.dao.PresenceDao;
import databaseLogic.factory.DaoFactory;
import entity.Report;
import servises.configManager.ConfigManager;
import servises.messageManager.MessageManager;
import servises.parameterManager.ParameterManager;
import servises.presenceManager.PresenceManager;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public class AddPresenceCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        String page = ConfigManager.getProperty("pastReports");

        List<Report> pastReportList = (List<Report>) request.getSession().getAttribute("pastReportList");
        String index = request.getParameter("index");
        Report report = pastReportList.get(Integer.parseInt(index));
        String sCount = request.getParameter("presence");

        ParameterManager pm = new ParameterManager();
        MessageManager message = new MessageManager();


        if (!pm.isNumberCorrect(sCount)) {
            request.setAttribute("errorNumber", message.getProperty("errorNumber"));
            return page;
        }

        int count = Integer.parseInt(sCount);
        PresenceManager presenceManager = new PresenceManager();
        int result=presenceManager.addPresence(report.getId(), count);

//        PresenceDao presenceDao = DaoFactory.getPresenceDao();
//        int result = presenceDao.addPresence(report.getId(), count);
//        presenceDao.closeConnection();

        if (result != 0) {
            Map<Long, Integer> pastReportPresence = (Map<Long, Integer>) request.getSession().getAttribute("pastReportPresence");
            pastReportPresence.put(report.getId(), count);
            request.getSession().setAttribute("pastReportPresence", pastReportPresence);
            request.setAttribute("successfulChanges", message.getProperty("successfulChanges"));
        }

        return page;
    }
}
