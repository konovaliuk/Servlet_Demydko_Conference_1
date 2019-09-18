package commands.impl;

import commands.Command;
import servises.configManager.ConfigManager;
import servises.languageManager.LanguageManager;

import javax.servlet.http.HttpServletRequest;

public class ChangeLanguageCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) {
        String language = request.getParameter("language");
        String requestURI = request.getParameter("requestURI");

        LanguageManager languageManager = new LanguageManager();
        language = languageManager.setLanguageToSession(language);
        request.getSession().setAttribute("language", language);
        if (requestURI.endsWith("jsp")) {
            String[] array = requestURI.split("[/.]");
            return ConfigManager.getProperty(array[array.length - 2]);
        }
        return ConfigManager.getProperty("index");
    }
}
