package commands.commandHelpers;

import entity.Report;
import servises.paginationManager.PaginationManager;
import servises.presenceManager.PresenceManager;
import servises.registerManager.RegisterManager;

import java.util.List;
import java.util.Map;


public class PastReportsHelper implements CommandHelper {
    private String requestOffset;
    private String requestMaxCount;
    private Integer sessionOffset;
    private Integer sessionMaxCount;

    private List<Integer> buttons;
    private int offset;
    private List<Report> pastConferenceList;
    private Map<Long, Integer> pastReportPresence;
    private int maxCount;

    public PastReportsHelper(String requestOffset, String requestMaxCount, Integer sessionOffset, Integer sessionMaxCount) {
        this.requestOffset = requestOffset;
        this.requestMaxCount = requestMaxCount;
        this.sessionOffset = sessionOffset;
        this.sessionMaxCount = sessionMaxCount;
    }

    @Override
    public String handle() {
        PaginationManager pm = new PaginationManager(requestOffset, requestMaxCount, sessionOffset, sessionMaxCount);
        pm.pagination("past");
        offset = pm.getOffset();
        maxCount = pm.getMaxCount();
        pastConferenceList = pm.getConferenceList();
        buttons = pm.getButtons();
        presence();
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
