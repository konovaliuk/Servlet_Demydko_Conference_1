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

public class FutureReportsCommand implements Command {


    @Override
    public String execute(HttpServletRequest request) {
        ReportDao reportDao = DaoFactory.getReportDao();
        List<Report> list = reportDao.getFutureConference();
        reportDao.closeConnection();
        User user = (User) request.getSession().getAttribute("user");
        if (user.getPosition().equals("User")) {
            RegisterDao registerDao = DaoFactory.getRegisterDao();
            List<Long> registerList = registerDao.getReportsIdByUserId(user.getId());
            registerDao.closeConnection();
            for (Long id : registerList) {
                for (Report report : list) {
                    if (report.getId() == id) {
                        report.setIsUserRegistered(true);
                    }
                }
            }
        }
        request.getSession().setAttribute("reportList", list);
        return ConfigManager.getProperty("futureReports");
    }
}
