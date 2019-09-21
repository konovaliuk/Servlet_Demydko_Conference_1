package commands.impl;

import commands.Command;
import commands.commandHelpers.impl.AddBonusesHelper;
import servises.configManager.ConfigManager;
import servises.messageManager.MessageManager;

import javax.servlet.http.HttpServletRequest;

public class AddBonusesCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) {

        String bonuses = request.getParameter("bonuses");
        String email = request.getParameter("email");

        AddBonusesHelper helper = new AddBonusesHelper(bonuses, email);
        String result = helper.handle();

        MessageManager message = new MessageManager();
        request.setAttribute(result, message.getProperty(result));
        return ConfigManager.getProperty("cabinet");
    }
}
