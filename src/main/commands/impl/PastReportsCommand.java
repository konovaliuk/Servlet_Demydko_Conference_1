package commands.impl;

import commands.Command;
import databaseLogic.dao.RegisterDao;
import databaseLogic.dao.ReportDao;
import databaseLogic.factory.DaoFactory;
import entity.Report;
import entity.User;
import servises.configManager.ConfigManager;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class PastReportsCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) {
        ReportDao reportDao = DaoFactory.getReportDao();
        List<Report> list = reportDao.getPastConference();
        reportDao.closeConnection();
        request.getSession().setAttribute("pastReportList", list);
        return ConfigManager.getProperty("pastReports");
    }

}

