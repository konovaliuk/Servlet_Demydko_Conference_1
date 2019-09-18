package commands.actionFactory;

import commands.Command;
import commands.commandEnum.CommandEnum;

import servises.configManager.ConfigManager;
import servises.messageManager.MessageManager;

import javax.servlet.http.HttpServletRequest;
import java.util.MissingResourceException;

public class ActionFactory {
    public Command defineCommand(HttpServletRequest request) {
        Command current;
        String action = request.getParameter("command");
        try {
            CommandEnum currentEnum = CommandEnum.valueOf(action.toUpperCase());
            current = currentEnum.getCurrentCommand();
        } catch (NullPointerException| MissingResourceException|IllegalArgumentException e) {
            String path = request.getRequestURI() + "?" + request.getQueryString();
            request.setAttribute("wrongAction", path);
            CommandEnum currentEnum = CommandEnum.valueOf("ERROR");
            current = currentEnum.getCurrentCommand();
            return current;
        }
        return current;
    }
}
