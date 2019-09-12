package commands.actionFactory;

import commands.Command;
import commands.commandEnum.CommandEnum;
import exceptions.PropertyNotFoundException;
import servises.messageManager.MessageManager;

import javax.servlet.http.HttpServletRequest;

public class ActionFactory {
    public Command defineCommand(HttpServletRequest request) {
        MessageManager message = new MessageManager();
        Command current = null;
        String action = request.getParameter("command");
        try {
            CommandEnum currentEnum = CommandEnum.valueOf(action.toUpperCase());
            current = currentEnum.getCurrentCommand();
        } catch (IllegalArgumentException e) {
            request.setAttribute("wrongAction", action
                    + message.getProperty("message.wrongaction"));
        }
        return current;
    }
}
