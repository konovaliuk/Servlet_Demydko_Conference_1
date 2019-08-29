package commands.impl;

import commands.Command;
import servises.configManager.ConfigManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LogoutCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute("user");
        return ConfigManager.getProperty("index");
    }
}
