package commands.commandHelpers;

import entity.Report;
import entity.User;
import servises.paginationManager.PaginationManager;
import servises.registerManager.RegisterManager;

import java.util.List;
import java.util.Map;

public class FutureReportsHelper implements CommandHelper {
    private RegisterManager registerManager = new RegisterManager();
    private String requestOffset;
    private String requestMaxCount;
    private Integer sessionOffset;
    private Integer sessionMaxCount;
    private User user;

    private List<Integer> buttons;
    private int offset;
    private Map<Long, Integer> countOfVisitors;
    private List<Report> futureConferenceList;
    private int maxCount;

    public FutureReportsHelper(String requestOffset, String requestMaxCount, Integer sessionOffset, Integer sessionMaxCount, User user) {
        this.requestOffset = requestOffset;
        this.requestMaxCount = requestMaxCount;
        this.sessionOffset = sessionOffset;
        this.sessionMaxCount = sessionMaxCount;
        this.user = user;
    }

    @Override
    public String handle() {
        PaginationManager pm = new PaginationManager(requestOffset, requestMaxCount, sessionOffset, sessionMaxCount);
        pm.pagination("future");
        offset = pm.getOffset();
        maxCount = pm.getMaxCount();
        futureConferenceList = pm.getConferenceList();
        buttons = pm.getButtons();
        checkRegistrationForUser();
        countOfVisitors();
        return "futureReports";
    }

    private void checkRegistrationForUser() {
        if (user.getPosition().equals("User")) {
            List<Long> registerList = registerManager.getReportsIdByUserId(user);
            registerManager.checkRegistrationForUser(futureConferenceList, registerList);
        }
    }

    private void countOfVisitors() {
        countOfVisitors = registerManager.getCountOfVisitors(futureConferenceList);
    }

    public List<Integer> getButtons() {
        return buttons;
    }

    public int getMaxCount() {
        return maxCount;
    }

    public int getOffset() {
        return offset;
    }

    public Map<Long, Integer> getCountOfVisitors() {
        return countOfVisitors;
    }

    public List<Report> getFutureConferenceList() {
        return futureConferenceList;
    }
}
