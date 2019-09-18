package commands.commandHelpers;

import entity.Speaker;
import entity.User;
import servises.parameterManager.ParameterManager;
import servises.reportManager.ReportManager;
import servises.spaekerManager.SpeakerManager;

public class OfferReportHelper implements CommandHelper {

    private String theme;
    private User user;

    public OfferReportHelper(String theme, User user) {
        this.theme = theme;
        this.user = user;
    }

    @Override
    public String handle() {
        ParameterManager pm = new ParameterManager();
        if (theme == null || theme.isEmpty()) {
            return "noActionDone";
        }
        if (!pm.isThemeCorrect(theme)) {
            return "errorTheme";
        }
        SpeakerManager speakerManager = new SpeakerManager();
        Speaker speaker = speakerManager.getSpeakerById(user.getId());
        ReportManager reportManager = new ReportManager();
        int result = reportManager.addReport(theme, speaker);
        if (result != 0) {
            return "successfulChanges";
        }
        return "cabinet";
    }
}
