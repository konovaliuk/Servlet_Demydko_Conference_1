package commands.impl;

import commands.Command;
import databaseLogic.dao.RegisterDao;
import databaseLogic.factory.DaoFactory;
import entity.Address;
import entity.Report;
import entity.User;
import servises.configManager.ConfigManager;
import servises.mailManager.MailManager;
import servises.messageManager.MessageManager;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class ConferenceRegisterCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) {
        String page = ConfigManager.getProperty("futureReports");
        List<Report> reportList = (List<Report>) request.getSession().getAttribute("reportList");
        User user = (User) request.getSession().getAttribute("user");
        String sIndex = request.getParameter("index");
        int index = Integer.parseInt(sIndex);

        Report r = reportList.get(index);
        if (r.getIsUserRegistered()) {
            request.setAttribute("errorAlreadyRegistered", MessageManager.getProperty("alreadyRegistered"));
            request.setAttribute("reportId", r.getId());
            return page;
        }

        RegisterDao registerDao = DaoFactory.getRegisterDao();
        int result = registerDao.userRegister(user.getId(), reportList.get(index).getId());
        registerDao.closeConnection();


        if (result != 0) {
            Report report = reportList.get(index);
            report.setIsUserRegistered(true);
            reportList.set(index, report);
            request.getSession().setAttribute("reportList", reportList);
            MailManager.notifyUserRegistration(user,report);
        }

        return page;
    }
}
