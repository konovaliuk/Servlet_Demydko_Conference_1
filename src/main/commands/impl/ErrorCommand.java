package commands.impl;

import commands.Command;
import servises.configManager.ConfigManager;

import javax.servlet.http.HttpServletRequest;

public class ErrorCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) {
        return ConfigManager.getProperty("error");
    }
}
