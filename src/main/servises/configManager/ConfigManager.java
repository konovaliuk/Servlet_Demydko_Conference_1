package servises.configManager;

import java.util.ResourceBundle;

public class ConfigManager {
    private final static ResourceBundle resourceBundle = ResourceBundle.getBundle("commandConfig");
    
    private ConfigManager() { }
    public static String getProperty(String key) {
        return resourceBundle.getString(key);
    }
}
