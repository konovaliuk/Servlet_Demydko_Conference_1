package servises.paginationManager;

import entity.Report;
import servises.reportManager.ReportManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PaginationManager {

    private String requestOffset;
    private String requestMaxCount;
    private Integer sessionOffset;
    private Integer sessionMaxCount;

    private int offset;
    private List<Report> conferenceList;
    private int maxCount;
    private List<Integer> buttons;

    public PaginationManager(String requestOffset, String requestMaxCount, Integer sessionOffset, Integer sessionMaxCount) {
        this.requestOffset = requestOffset;
        this.requestMaxCount = requestMaxCount;
        this.sessionOffset = sessionOffset;
        this.sessionMaxCount = sessionMaxCount;
    }

    public void pagination(String report) {

        ReportManager reportManager = new ReportManager();
        int countOfReports = 0;
        if (report.equals("future")) {
             countOfReports = reportManager.getCountOfFutureReports();
        }else {
            countOfReports = reportManager.getCountOfPastReports();
        }

        if (requestOffset == null && requestMaxCount == null) {
            maxCount = (sessionMaxCount != null) ? sessionMaxCount : 5;
            offset = (sessionOffset != null) ? sessionOffset : 0;

            if (report.equals("future")) {
                conferenceList = reportManager.getFutureConference(offset, maxCount);
            } else {
                conferenceList = reportManager.getPastConference(offset, maxCount);
            }

            buttons = getButtons(countOfReports, maxCount);
        } else {
            if (requestMaxCount == null) {
                if (sessionMaxCount == null) {
                    maxCount = 5;
                } else {
                    maxCount = sessionMaxCount;
                }
            } else {
                maxCount = Integer.parseInt(requestMaxCount);
                buttons = getButtons(countOfReports, maxCount);
            }
            if (requestOffset != null) {
                offset = Integer.parseInt(requestOffset) * maxCount - maxCount;
            } else if (sessionOffset != null) {
                offset = sessionOffset;
            } else {
                offset = 0;
            }

            if (report.equals("future")) {
                conferenceList = reportManager.getFutureConference(offset, maxCount);
            } else {
                conferenceList = reportManager.getPastConference(offset, maxCount);
            }
        }
    }



    private List<Integer> getButtons(int amountOfReports, int maxCountPerPage) {
        double buttons = amountOfReports / (double) maxCountPerPage;
        buttons = Math.ceil(buttons);
        List<Integer> list = new ArrayList<>();
        for (int i = 1; i <= buttons; i++) {
            list.add(i);
        }
        return list;
    }

    public int getOffset() {
        return offset;
    }

    public int getMaxCount() {
        return maxCount;
    }

    public List<Report> getConferenceList() {
        return conferenceList;
    }

    public List<Integer> getButtons() {
        return buttons;
    }
}
