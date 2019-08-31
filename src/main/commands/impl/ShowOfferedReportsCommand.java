package commands.impl;

import commands.Command;
import databaseLogic.dao.ReportDao;
import databaseLogic.factory.DaoFactory;
import entity.Report;
import servises.configManager.ConfigManager;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class ShowOfferedReportsCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) {
        ReportDao reportDao = DaoFactory.getReportDao();
        List<Report> list = reportDao.getOfferedConference();
        reportDao.closeConnection();
        request.getSession().setAttribute("offeredReportList", list);
        return ConfigManager.getProperty("offeredReports");
    }
}
