package commands.impl;

import commands.Command;
import databaseLogic.dao.RegisterDao;
import databaseLogic.factory.DaoFactory;
import entity.Report;
import servises.configManager.ConfigManager;
import servises.messageManager.MessageManager;
import servises.parameterManager.ParameterManager;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class AddPresenceCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        String page = ConfigManager.getProperty("pastReports");

        List<Report> pastReportList = (List<Report>) request.getSession().getAttribute("pastReportList");
        String index = request.getParameter("index");
        Report report = pastReportList.get(Integer.parseInt(index));
        String count = request.getParameter("presence");

        if (!ParameterManager.isNumberCorrect(count)) {
            request.setAttribute("errorNumber", MessageManager.getProperty("numberIncorrect"));
            return page;
        }

        RegisterDao registerDao = DaoFactory.getRegisterDao();
        int result = registerDao.addPresence(report.getId(), Integer.parseInt(count));
        registerDao.closeConnection();


        if (result != 0) {
            request.setAttribute("successfulChanges", MessageManager.getProperty("successfulChanges"));
        }

        return page;
    }
}
