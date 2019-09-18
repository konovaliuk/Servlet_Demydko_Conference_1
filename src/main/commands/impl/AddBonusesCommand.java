package commands.impl;

import commands.Command;
import commands.commandHelpers.AddBonusesHelper;
import databaseLogic.dao.SpeakerDao;
import databaseLogic.factory.DaoFactory;
import entity.Speaker;
import servises.configManager.ConfigManager;
import servises.mailManager.MailManager;
import servises.messageManager.MessageManager;
import servises.parameterManager.ParameterManager;
import servises.spaekerManager.SpeakerManager;
import servises.userManager.UserManager;

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
