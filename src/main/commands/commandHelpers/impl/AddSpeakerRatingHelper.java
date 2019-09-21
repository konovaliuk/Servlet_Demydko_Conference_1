package commands.commandHelpers.impl;

import commands.commandHelpers.CommandHelper;
import entity.Speaker;
import org.apache.log4j.Logger;
import servises.parameterManager.ParameterManager;
import servises.spaekerManager.SpeakerManager;

public class AddSpeakerRatingHelper implements CommandHelper {
    private Logger logger = Logger.getLogger(AddSpeakerRatingHelper.class);
    private String rating;
    private String email;

    public AddSpeakerRatingHelper(String rating, String email) {
        this.rating = rating;
        this.email = email;
    }

    @Override
    public String handle() {
        ParameterManager parameterManager = new ParameterManager();
        if (!parameterManager.isEmailCorrect(email)) {
            logger.info("Email was imputed incorrectly: " + email);
            return "errorEmailForm";
        }
        SpeakerManager speakerManager = new SpeakerManager();
        Speaker speaker = speakerManager.getSpeakerByEmail(email);

        if (speaker == null) {
            logger.info("Speaker with such " + email + " email does not exist");
            return "errorSpeakerNotExists";
        }
        speakerManager.addSpeakerRating(speaker, Integer.parseInt(rating));
        logger.info("Was added rating " + rating + " to speaker " + speaker.getEmail());
        return "successfulChanges";
    }
}
