package commands.impl;

import commands.Command;
import databaseLogic.dao.RegisterDao;
import databaseLogic.factory.DaoFactory;
import entity.Report;
import entity.User;
import servises.configManager.ConfigManager;
import servises.mailManager.MailManager;
import servises.messageManager.MessageManager;
import servises.registerManager.RegisterManager;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public class ConferenceRegisterCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) {
        String page = ConfigManager.getProperty("futureReports");
        List<Report> reportList = (List<Report>) request.getSession().getAttribute("reportList");
        User user = (User) request.getSession().getAttribute("user");
        String sIndex = request.getParameter("index");
        int index = Integer.parseInt(sIndex);

        Report r = reportList.get(index);
        MessageManager message = new MessageManager();
        MailManager mail = new MailManager();

        if (r.getIsUserRegistered()) {
            request.setAttribute("errorAlreadyRegistered", message.getProperty("errorAlreadyRegistered"));
            request.setAttribute("reportId", r.getId());
            return page;
        }

        RegisterManager registerManager = new RegisterManager();
        int result = registerManager.userRegister(user.getId(), reportList.get(index).getId());

        if (result != 0) {
            Report report = reportList.get(index);
            registerManager.makeUserRegistered(report);
            request.getSession().setAttribute("reportList", reportList);

            Map<Long, Integer> countOfVisitors = (Map<Long, Integer>) request.getSession().getAttribute("countOfVisitors");
            int count = countOfVisitors.get(report.getId());
            countOfVisitors.put(report.getId(), ++count);
            request.getSession().setAttribute("countOfVisitors", countOfVisitors);
            mail.notifyUserRegistration(user, report);
        }

        return page;
    }
}
