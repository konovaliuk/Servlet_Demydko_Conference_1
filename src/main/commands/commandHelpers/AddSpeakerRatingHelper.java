package commands.commandHelpers;

import entity.Speaker;
import servises.parameterManager.ParameterManager;
import servises.spaekerManager.SpeakerManager;

public class AddSpeakerRatingHelper implements CommandHelper {
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
            return "errorEmailForm";
        }
        SpeakerManager speakerManager = new SpeakerManager();
        Speaker speaker = speakerManager.getSpeakerByEmail(email);

        if (speaker == null) {
            return "errorSpeakerNotExists";
        }
        speakerManager.addSpeakerRating(speaker, Integer.parseInt(rating));
        return "successfulChanges";
    }
}
