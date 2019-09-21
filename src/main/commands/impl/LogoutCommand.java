package commands.impl;

import commands.Command;
import entity.User;
import org.apache.log4j.Logger;
import servises.configManager.ConfigManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;

public class LogoutCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) {
        Logger logger = Logger.getLogger(LogoutCommand.class);
        User user = (User) request.getSession().getAttribute("user");
        request.getSession().invalidate();
        logger.info("User " + user.getEmail() + " logged out of session");
        return ConfigManager.getProperty("index");
    }
}
