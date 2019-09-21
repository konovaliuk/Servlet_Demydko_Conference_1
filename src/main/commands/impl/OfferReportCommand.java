package commands.impl;

import commands.Command;
import commands.commandHelpers.impl.OfferReportHelper;
import entity.User;
import servises.configManager.ConfigManager;
import servises.messageManager.MessageManager;

import javax.servlet.http.HttpServletRequest;

public class OfferReportCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) {
        String theme = request.getParameter("theme");
        User user = (User) request.getSession().getAttribute("user");

        OfferReportHelper helper = new OfferReportHelper(theme,user);
        String result = helper.handle();

        MessageManager message = new MessageManager();
        request.setAttribute(result, message.getProperty(result));
        return ConfigManager.getProperty("cabinet");
    }
}
