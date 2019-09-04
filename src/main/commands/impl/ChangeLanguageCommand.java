package commands.impl;

import commands.Command;
import servises.configManager.ConfigManager;
import servises.messageManager.MessageManager;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

public class ChangeLanguageCommand implements Command {


    @Override
    public String execute(HttpServletRequest request) {
//        Locale locale = Locale.getDefault();
        Locale.setDefault(Locale.US);
        request.setAttribute("successfulChanges", MessageManager.getProperty("successfulChanges"));
        return ConfigManager.getProperty("cabinet");
    }
}
