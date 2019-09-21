package commands.impl;

import commands.Command;
import commands.commandHelpers.impl.AddSpeakerRatingHelper;
import servises.configManager.ConfigManager;
import servises.messageManager.MessageManager;
import javax.servlet.http.HttpServletRequest;

public class AddSpeakerRatingCommand implements Command {


    @Override
    public String execute(HttpServletRequest request) {
        String rating = request.getParameter("rating");
        String email = request.getParameter("email");

        AddSpeakerRatingHelper helper = new AddSpeakerRatingHelper(rating, email);
        String result = helper.handle();
        MessageManager message = new MessageManager();
        request.setAttribute(result, message.getProperty(result));

        return ConfigManager.getProperty("cabinet");
    }
}
