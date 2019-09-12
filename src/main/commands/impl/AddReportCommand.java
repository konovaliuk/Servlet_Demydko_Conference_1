package commands.impl;

import commands.Command;
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
        String page = ConfigManager.getProperty("cabinet");

        String sDate = request.getParameter("date");
        String sTime = request.getParameter("time");
        String theme = request.getParameter("theme");
        String city = request.getParameter("city");
        String street = request.getParameter("street");
        String building = request.getParameter("building");
        String room = request.getParameter("room");
        String email = request.getParameter("speakerEmail");

        ParameterManager parameterManager = new ParameterManager();
        MessageManager message = new MessageManager();

        if (parameterManager.isAllEmpty(sDate, sTime, theme, city, street, building, room, email)) {
            request.setAttribute("errorEmptyForm", message.getProperty("errorEmptyForm"));
            return page;
        }

        DateTimeManager dateTimeManager = new DateTimeManager();
        Date date = dateTimeManager.fromStringToSqlDate(sDate);

        if (new java.util.Date().getTime() > date.getTime()) {
            request.setAttribute("errorDate", message.getProperty("errorDate"));
            return page;
        }

        Address address = new Address(city, street, building, room);
        if (!parameterManager.isAddressCorrect(address)) {
            request.setAttribute("errorAddress", message.getProperty("addressIncorrect"));
            return page;
        }

        if (!parameterManager.isThemeCorrect(theme)) {
            request.setAttribute("errorTheme", message.getProperty("errorTheme"));
            return page;
        }
        SpeakerManager speakerManager = new SpeakerManager();
        Speaker speaker = speakerManager.getSpeakerByEmail(email);


        if (speaker == null) {
            request.setAttribute("errorSpeakerNotExists", message.getProperty("errorSpeakerNotExists"));
            return page;
        }
        Time time = dateTimeManager.fromStringToTime(sTime);
        Report report = new Report(theme, address, date, time, speaker);

        ReportManager reportManager = new ReportManager();
        int result = reportManager.addReport(report);

        if (result != 0) {
            MailManager mail = new MailManager();
            request.setAttribute("successfulChanges", message.getProperty("successfulChanges"));
            mail.notifySpeakerAppointment(speaker, report);
        }

        return page;
    }
}
