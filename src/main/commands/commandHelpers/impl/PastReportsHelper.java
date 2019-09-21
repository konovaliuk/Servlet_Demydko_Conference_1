package commands.commandHelpers.impl;

import commands.commandHelpers.CommandHelper;
import entity.Report;
import org.apache.log4j.Logger;
import servises.paginationManager.PaginationManager;
import servises.presenceManager.PresenceManager;

import java.util.List;
import java.util.Map;


public class PastReportsHelper implements CommandHelper {
    private Logger logger = Logger.getLogger(PastReportsHelper.class);
    private String requestButton;
    private String requestMaxCount;
    private Integer sessionOffset;
    private Integer sessionMaxCount;
    private Integer sessionButton;

    private List<Integer> buttons;
    private int offset;
    private List<Report> pastConferenceList;
    private Map<Long, Integer> pastReportPresence;
    private int maxCount;
    private int currentButton;

    public PastReportsHelper(String requestButton, Integer sessionButton, String requestMaxCount, Integer sessionOffset, Integer sessionMaxCount) {
        this.requestButton = requestButton;
        this.sessionButton = sessionButton;
        this.requestMaxCount = requestMaxCount;
        this.sessionOffset = sessionOffset;
        this.sessionMaxCount = sessionMaxCount;
    }

    @Override
    public String handle() {
        PaginationManager pm = new PaginationManager(requestButton, sessionButton, requestMaxCount, sessionOffset, sessionMaxCount);
        pm.pagination("past");
        offset = pm.getOffset();
        maxCount = pm.getMaxCount();
        pastConferenceList = pm.getConferenceList();
        buttons = pm.getButtons();
        currentButton = pm.getCurrentButton();
        presence();
        logger.info("Were selected past reports to view");
        return "pastReports";
    }

    private void presence() {
        PresenceManager presenceManager = new PresenceManager();
        pastReportPresence = presenceManager.getPresence(pastConferenceList);
    }

    public List<Integer> getButtons() {
        return buttons;
    }

    public int getOffset() {
        return offset;
    }

    public int getCurrentButton() {
        return currentButton;
    }

    public List<Report> getPastConferenceList() {
        return pastConferenceList;
    }

    public int getMaxCount() {
        return maxCount;
    }

    public Map<Long, Integer> getPastReportPresence() {
        return pastReportPresence;
    }
}
