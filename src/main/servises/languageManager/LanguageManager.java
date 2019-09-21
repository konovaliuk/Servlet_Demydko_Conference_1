package servises.languageManager;


import entity.User;

public class LanguageManager {
    /**
     * Sets language to {@link User}
     */
    public String setLanguageToUser(String language) {
        if (language == null) {
            return language = "EN";
        }
        switch (language) {
            case "ua_UA":
                return language = "UA";
            case "ru_RU":
                return language = "RU";
            default:
                return language = "EN";
        }

    }

    /**
     * Sets language to session
     */
    public String setLanguageToSession(String language) {
        if (language == null) {
            return language = "EN";
        }
        switch (language) {
            case "UA":
                return language = "ua_UA";
            case "RU":
                return language = "ru_RU";
            default:
                return language = "en_US";
        }
    }


}
