package commands.impl;

import commands.Command;
import databaseLogic.dao.ReportDao;
import databaseLogic.factory.DaoFactory;
import entity.Report;
import servises.configManager.ConfigManager;
import servises.reportManager.ReportManager;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class DeletePastReportCommand implements Command {


    @Override
    public String execute(HttpServletRequest request) {
        List<Report> reports = (List<Report>) request.getSession().getAttribute("pastReportList");
        String reportId = request.getParameter("reportId");
        ReportManager reportManager = new ReportManager();
        reportManager.deleteReport(reports,Long.parseLong(reportId));
        return ConfigManager.getProperty("pastReports");
    }
}
