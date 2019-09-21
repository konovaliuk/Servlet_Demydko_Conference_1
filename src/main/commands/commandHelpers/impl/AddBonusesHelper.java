package commands.commandHelpers.impl;

import commands.commandHelpers.CommandHelper;
import entity.Speaker;
import org.apache.log4j.Logger;
import servises.parameterManager.ParameterManager;
import servises.spaekerManager.SpeakerManager;

public class AddBonusesHelper implements CommandHelper {
    private Logger logger = Logger.getLogger(AddBonusesHelper.class);
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
            logger.info("Number was input incorrectly: " + bonuses);
            return "errorNumber";
        }
        if (!pm.isEmailCorrect(email)) {
            logger.info("Email was imputed incorrectly: " + email);
            return "errorEmailForm";
        }
        SpeakerManager speakerManager = new SpeakerManager();
        Speaker speaker = speakerManager.getSpeakerByEmail(email);
        if (speaker == null) {
            logger.info("Speaker with such " + email + " email does not exist");
            return "errorSpeakerNotExists";
        }
        int allBonuses = speakerManager.setSpeakerBonuses(Integer.parseInt(bonuses), speaker);
        speakerManager.addBonusesToSpeaker(speaker, allBonuses);
        logger.info("Were added " + bonuses + " bonuses to speaker " + speaker.getEmail());
        return "successfulChanges";
    }
}
