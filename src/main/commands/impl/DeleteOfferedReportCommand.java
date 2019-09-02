package commands.impl;

import commands.Command;
import databaseLogic.dao.ReportDao;
import databaseLogic.factory.DaoFactory;
import entity.Report;
import servises.configManager.ConfigManager;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class DeleteOfferedReportCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) {
        List<Report> reports = (List<Report>) request.getSession().getAttribute("offeredReportList");
        String reportId = request.getParameter("reportId");
        long id = Long.parseLong(reportId);
        boolean isPresent = false;

        for (Report report:reports) {
            if (report.getId() == id) {
                reports.remove(report);
                isPresent = true;
                break;
            }
        }
        if (isPresent) {
            ReportDao reportDao = DaoFactory.getReportDao();
            reportDao.deleteReport(id);
            reportDao.closeConnection();
        }
        return ConfigManager.getProperty("offeredReports");
    }
}
