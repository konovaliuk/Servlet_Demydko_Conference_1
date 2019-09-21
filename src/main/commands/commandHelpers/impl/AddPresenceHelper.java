package commands.commandHelpers.impl;

import commands.commandHelpers.CommandHelper;
import entity.Report;
import org.apache.log4j.Logger;
import servises.parameterManager.ParameterManager;
import servises.presenceManager.PresenceManager;

import java.util.List;
import java.util.Map;

public class AddPresenceHelper implements CommandHelper {
    private Logger logger = Logger.getLogger(AddPresenceHelper.class);
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
            logger.info("Number was input incorrectly: " + sCount);
            return "errorNumber";
        }
        int count = Integer.parseInt(sCount);
        PresenceManager presenceManager = new PresenceManager();
        int result = presenceManager.addPresence(report.getId(), count);

        if (result != 0) {
            pastReportPresence.put(report.getId(), count);
            logger.info("Was added presence  " + count + " to report with id " + report.getId());
        }
        return "successfulChanges";
    }
}
