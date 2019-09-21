package commands.commandHelpers.impl;

import commands.commandHelpers.CommandHelper;
import entity.Report;
import org.apache.log4j.Logger;
import servises.paginationManager.PaginationManager;
import servises.reportManager.ReportManager;

import java.util.List;

public class DeleteOfferedReportHelper implements CommandHelper {
    private Logger logger = Logger.getLogger(DeleteOfferedReportHelper.class);
    private List<Report> reports;
    private String reportId;
    private Integer sessionButton;
    private Integer sessionOffset;
    private Integer sessionMaxCount;

    private List<Integer> buttons;
    private List<Report> offeredConferenceList;
    private int currentButton;


    public DeleteOfferedReportHelper(List<Report> reports, String reportId, Integer sessionButton, Integer sessionOffset, Integer sessionMaxCount) {
        this.reports = reports;
        this.reportId = reportId;
        this.sessionButton = sessionButton;
        this.sessionOffset = sessionOffset;
        this.sessionMaxCount = sessionMaxCount;
    }

    @Override
    public String handle() {
        ReportManager reportManager = new ReportManager();
        reportManager.deleteReport(reports, Long.parseLong(reportId));
        PaginationManager pm = new PaginationManager(null, sessionButton, null, sessionOffset, sessionMaxCount);
        pm.pagination("offered");
        buttons = pm.getButtons();
        offeredConferenceList = pm.getConferenceList();
        currentButton = pm.getCurrentButton();
        logger.info("Offered report was deleted");
        return "offeredReports";
    }

    public List<Integer> getButtons() {
        return buttons;
    }

    public List<Report> getOfferedConferenceList() {
        return offeredConferenceList;
    }

    public int getCurrentButton() {
        return currentButton;
    }
}
