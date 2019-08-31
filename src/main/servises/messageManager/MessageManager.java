package servises.messageManager;

import entity.Report;
import entity.Speaker;
import servises.dateTimeManager.DateTimeManager;
import servises.mailManager.MailManager;
import servises.mailManager.MailThread;

import java.util.ResourceBundle;

public class MessageManager {
    private final static ResourceBundle resourceBundle = ResourceBundle.getBundle("messages");
    private MessageManager() { }
    public static String getProperty(String key) {
        return resourceBundle.getString(key);
    }

}
