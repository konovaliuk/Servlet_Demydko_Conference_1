package commands.impl;

import commands.Command;
import commands.commandHelpers.AddReportHelper;
import databaseLogic.dao.ReportDao;
import databaseLogic.dao.SpeakerDao;
import databaseLogic.factory.DaoFactory;
import entity.Address;
import entity.Report;
import entity.Speaker;
import servises.configManager.ConfigManager;
import servises.dateTimeManager.DateTimeManager;
import servises.mailManager.MailManager;
import servises.messageManager.MessageManager;
import servises.parameterManager.ParameterManager;
import servises.reportManager.ReportManager;
import servises.spaekerManager.SpeakerManager;

import javax.servlet.http.HttpServletRequest;
import java.sql.Time;
import java.sql.Date;

public class AddReportCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) {

        String date = request.getParameter("date");
        String time = request.getParameter("time");
        String theme = request.getParameter("theme");
        String city = request.getParameter("city");
        String street = request.getParameter("street");
        String building = request.getParameter("building");
        String room = request.getParameter("room");
        String email = request.getParameter("speakerEmail");

        AddReportHelper helper = new AddReportHelper(date, time, theme, city, street, building, room, email);
        String result = helper.handle();
        MessageManager message = new MessageManager();
        request.setAttribute(result, message.getProperty(result));
        return ConfigManager.getProperty("cabinet");
    }
}
