package commands.commandHelpers;

import entity.Report;
import entity.User;
import servises.mailManager.MailManager;
import servises.messageManager.MessageManager;
import servises.registerManager.RegisterManager;

import java.util.List;
import java.util.Map;

public class ConferenceRegisterHelper implements CommandHelper {

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
        }

        return "success";
    }

    public Long getReportId() {
        return reportId;
    }
}
