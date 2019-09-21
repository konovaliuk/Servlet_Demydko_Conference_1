package commands.commandHelpers.impl;

import commands.commandHelpers.CommandHelper;
import entity.Report;
import org.apache.log4j.Logger;
import servises.paginationManager.PaginationManager;

import java.util.List;

public class ShowOfferedReportsHelper implements CommandHelper {
    private Logger logger = Logger.getLogger(ShowOfferedReportsHelper.class);
    private String requestButton;
    private String requestMaxCount;
    private Integer sessionButton;
    private Integer sessionOffset;
    private Integer sessionMaxCount;

    private List<Integer> buttons;
    private int offset;
    private List<Report> offeredConferenceList;
    private int maxCount;
    private int currentButton;


    public ShowOfferedReportsHelper(String requestButton, String requestMaxCount, Integer sessionButton, Integer sessionOffset, Integer sessionMaxCount) {
        this.requestButton = requestButton;
        this.requestMaxCount = requestMaxCount;
        this.sessionButton = sessionButton;
        this.sessionOffset = sessionOffset;
        this.sessionMaxCount = sessionMaxCount;
    }

    @Override
    public String handle() {
        PaginationManager pm = new PaginationManager(
                requestButton, sessionButton, requestMaxCount, sessionOffset, sessionMaxCount);
        pm.pagination("offered");
        offset = pm.getOffset();
        maxCount = pm.getMaxCount();
        offeredConferenceList = pm.getConferenceList();
        buttons = pm.getButtons();
        currentButton = pm.getCurrentButton();
        logger.info("Were selected offered reports to view");
        return "offeredReports";
    }

    public List<Integer> getButtons() {
        return buttons;
    }

    public int getOffset() {
        return offset;
    }

    public List<Report> getOfferedConferenceList() {
        return offeredConferenceList;
    }

    public int getMaxCount() {
        return maxCount;
    }

    public int getCurrentButton() {
        return currentButton;
    }
}
