package commands.commandHelpers.impl;

import commands.commandHelpers.CommandHelper;
import entity.Report;
import entity.User;
import org.apache.log4j.Logger;
import servises.mailManager.MailManager;
import servises.registerManager.RegisterManager;

import java.util.List;
import java.util.Map;

public class ConferenceRegisterHelper implements CommandHelper {
    private Logger logger = Logger.getLogger(DeleteOfferedReportHelper.class);
    private List<Report> reportList;
    private User user;
    private String sIndex;
    private Map<Long, Integer> countOfVisitors;
    private Long reportId;

    public ConferenceRegisterHelper(List<Report> reportList, User user, String sIndex, Map<Long, Integer> countOfVisitors) {
        this.reportList = reportList;
        this.user = user;
        this.sIndex = sIndex;
        this.countOfVisitors = countOfVisitors;
    }

    @Override
    public String handle() {
        int index = Integer.parseInt(sIndex);
        Report r = reportList.get(index);

        MailManager mail = new MailManager();

        if (r.getIsUserRegistered()) {
            reportId = r.getId();
            logger.info("User  " + user.getEmail() + " try to register again");
            return "errorAlreadyRegistered";
        }

        RegisterManager registerManager = new RegisterManager();
        int result = registerManager.userRegister(user.getId(), reportList.get(index).getId());

        if (result != 0) {
            Report report = reportList.get(index);
            registerManager.makeUserRegistered(report);
            int count = countOfVisitors.get(report.getId());
            countOfVisitors.put(report.getId(), ++count);
            mail.notifyUserRegistration(user, report);
            logger.info("User  " + user.getEmail() + " has successfully registered on conference with id " + report.getId());
        }
        return "success";
    }

    public Long getReportId() {
        return reportId;
    }
}
