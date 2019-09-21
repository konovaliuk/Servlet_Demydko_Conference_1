package commands.commandHelpers.impl;

import commands.commandHelpers.CommandHelper;
import entity.Report;
import entity.User;
import org.apache.log4j.Logger;
import servises.paginationManager.PaginationManager;
import servises.registerManager.RegisterManager;

import java.util.List;
import java.util.Map;

public class FutureReportsHelper implements CommandHelper {
    private Logger logger = Logger.getLogger(LoginHelper.class);
    private RegisterManager registerManager = new RegisterManager();
    private String requestButton;
    private String requestMaxCount;
    private Integer sessionOffset;
    private Integer sessionMaxCount;
    private Integer sessionButton;
    private User user;

    private List<Integer> buttons;
    private int offset;
    private Map<Long, Integer> countOfVisitors;
    private List<Report> futureConferenceList;
    private int maxCount;
    private int currentButton;

    public FutureReportsHelper(String requestButton, Integer sessionButton, String requestMaxCount, Integer sessionOffset, Integer sessionMaxCount, User user) {
        this.requestButton = requestButton;
        this.sessionButton = sessionButton;
        this.requestMaxCount = requestMaxCount;
        this.sessionOffset = sessionOffset;
        this.sessionMaxCount = sessionMaxCount;
        this.user = user;
    }

    @Override
    public String handle() {
        PaginationManager pm = new PaginationManager(requestButton, sessionButton, requestMaxCount, sessionOffset, sessionMaxCount);
        pm.pagination("future");
        offset = pm.getOffset();
        maxCount = pm.getMaxCount();
        futureConferenceList = pm.getConferenceList();
        buttons = pm.getButtons();
        currentButton = pm.getCurrentButton();
        checkRegistrationForUser();
        countOfVisitors();
        logger.info("Were selected future reports to view");
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

    public int getCurrentButton() {
        return currentButton;
    }

    public Map<Long, Integer> getCountOfVisitors() {
        return countOfVisitors;
    }

    public List<Report> getFutureConferenceList() {
        return futureConferenceList;
    }
}
