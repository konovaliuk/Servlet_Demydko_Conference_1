package commands.commandHelpers;

import entity.Speaker;
import servises.parameterManager.ParameterManager;
import servises.spaekerManager.SpeakerManager;

public class AddBonusesHelper implements CommandHelper {

    private String bonuses;
    private String email;

    public AddBonusesHelper(String bonuses, String email) {
        this.bonuses = bonuses;
        this.email = email;
    }

    @Override
    public String handle() {
        ParameterManager pm = new ParameterManager();
        if (!pm.isNumberCorrect(bonuses)) {
            return "errorNumber";
        }
        if (!pm.isEmailCorrect(email)) {
            return "errorEmailForm";
        }
        SpeakerManager speakerManager = new SpeakerManager();
        Speaker speaker = speakerManager.getSpeakerByEmail(email);
        if (speaker == null) {
            return "errorSpeakerNotExists";
        }
        int allBonuses = speakerManager.setSpeakerBonuses(Integer.parseInt(bonuses), speaker);
        speakerManager.addBonusesToSpeaker(speaker, allBonuses);
        return "successfulChanges";
    }
}
