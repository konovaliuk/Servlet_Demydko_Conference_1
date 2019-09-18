package commands.commandHelpers;

import entity.Report;
import servises.parameterManager.ParameterManager;
import servises.presenceManager.PresenceManager;

import java.util.List;
import java.util.Map;

public class AddPresenceHelper implements CommandHelper {

    private List<Report> pastReportList;
    private Map<Long, Integer> pastReportPresence;
    private String index;
    private String sCount;

    public AddPresenceHelper(List<Report> pastReportList, Map<Long, Integer> pastReportPresence, String index, String sCount) {
        this.pastReportList = pastReportList;
        this.pastReportPresence = pastReportPresence;
        this.index = index;
        this.sCount = sCount;
    }

    @Override
    public String handle() {
        Report report = pastReportList.get(Integer.parseInt(index));
        ParameterManager pm = new ParameterManager();
        if (!pm.isNumberCorrect(sCount)) {
            return "errorNumber";
        }
        int count = Integer.parseInt(sCount);
        PresenceManager presenceManager = new PresenceManager();
        int result = presenceManager.addPresence(report.getId(), count);

        if (result != 0) {
            pastReportPresence.put(report.getId(), count);
        }
        return "successfulChanges";
    }
}
