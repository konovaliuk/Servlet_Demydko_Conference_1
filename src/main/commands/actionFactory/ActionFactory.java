package commands.actionFactory;

import commands.Command;
import commands.commandEnum.CommandEnum;
import entity.User;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.MissingResourceException;

public class ActionFactory {
    /**
     * @return  {@link Command} that is used in Controller
     */
    public Command defineCommand(HttpServletRequest request) {
        Logger logger = Logger.getLogger(ActionFactory.class);
        Command current;
        String action = request.getParameter("command");
        try {
            CommandEnum currentEnum = CommandEnum.valueOf(action.toUpperCase());
            current = currentEnum.getCurrentCommand();
        } catch (NullPointerException | MissingResourceException | IllegalArgumentException e) {
            String path = request.getRequestURI() + "?" + request.getQueryString();
            request.setAttribute("wrongAction", path);
            CommandEnum currentEnum = CommandEnum.valueOf("ERROR");
            current = currentEnum.getCurrentCommand();
            User user = (User) request.getSession().getAttribute("user");
            logger.info("Were selected incorrect path: " + path + " by user " + user.getEmail());
            return current;
        }
        return current;
    }
}
