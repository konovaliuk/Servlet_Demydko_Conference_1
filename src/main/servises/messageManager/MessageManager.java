package servises.messageManager;

import java.util.Locale;
import java.util.ResourceBundle;

public class MessageManager {
    private Locale locale = new Locale("en", "US");
    ;
    private static ResourceBundle resourceBundle;

    public MessageManager() {
        resourceBundle = ResourceBundle.getBundle("messages", locale);
    }

    public String getProperty(String key) {
        return resourceBundle.getString(key);
    }

    public void setLocale(String language) {
        switch (language) {
            case "UA":
                locale = new Locale("ua", "UA");
                break;
            case "RU":
                locale = new Locale("ru", "RU");
                break;
            default:
                locale = new Locale("en", "US");
        }
        resourceBundle = ResourceBundle.getBundle("messages", locale);
    }

}
