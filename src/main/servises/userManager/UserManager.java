package servises.userManager;

import entity.Speaker;

public class UserManager {
    public static int setSpeakerBonuses(int bonuses, Speaker speaker) {
        if (speaker.getRating() == 0) {
            return bonuses;
        }
        double result = bonuses += bonuses * (speaker.getRating() / 10.0);
        return (int) result;
    }
}
