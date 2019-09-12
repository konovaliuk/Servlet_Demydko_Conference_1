package servises.languageManager;

public class LanguageManager {

    public String setLanguageToUser(String language) {
        if (language == null) {
            return language = "EN";
        } else {
            switch (language) {
                case "ua_UA":
                    return language = "UA";
                case "ru_RU":
                    return language = "RU";
                default:
                    return language = "EN";
            }
        }
    }

    public String setLanguageToSession(String language) {
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
