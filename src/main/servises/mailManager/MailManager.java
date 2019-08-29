package servises.mailManager;

import java.util.ResourceBundle;

public class MailManager {
    private final static ResourceBundle resourceBundle = ResourceBundle.getBundle("mail");
    private MailManager() { }
    public static String getProperty(String key) {
        return resourceBundle.getString(key);
    }
}
