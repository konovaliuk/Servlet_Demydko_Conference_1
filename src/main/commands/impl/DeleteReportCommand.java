package commands.impl;

import commands.Command;
import databaseLogic.dao.ReportDao;
import databaseLogic.factory.DaoFactory;
import entity.Report;
import servises.configManager.ConfigManager;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class DeleteReportCommand implements Command {


    @Override
    public String execute(HttpServletRequest request) {
        List<Report> reports = (List<Report>) request.getSession().getAttribute("offeredReportList");
        String sIndex = request.getParameter("index");
        ReportDao reportDao = DaoFactory.getReportDao();
        int index = Integer.parseInt(sIndex);
        reportDao.deleteReport(reports.get(index).getId());
        reports.remove(index);
        return ConfigManager.getProperty("offeredReports");
    }
}
