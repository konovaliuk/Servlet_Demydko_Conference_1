package commands.impl;

import commands.Command;
import databaseLogic.dao.ReportDao;
import databaseLogic.factory.DaoFactory;
import entity.Report;
import servises.configManager.ConfigManager;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class FutureReportsCommand implements Command {


    @Override
    public String execute(HttpServletRequest request) {
        ReportDao reportDao = DaoFactory.getReportDao();
        List<Report> list = reportDao.getFutureConference();
        reportDao.closeConnection();
        request.getSession().setAttribute("reportList", list);
        return ConfigManager.getProperty("futureReports");
    }
}
