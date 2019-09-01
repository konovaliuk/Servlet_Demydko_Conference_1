package commands.impl;

import commands.Command;
import databaseLogic.dao.ReportDao;
import databaseLogic.dao.UserDao;
import databaseLogic.factory.DaoFactory;
import entity.Address;
import entity.Report;
import entity.Speaker;
import servises.configManager.ConfigManager;
import servises.dateTimeManager.DateTimeManager;
import servises.mailManager.MailManager;
import servises.messageManager.MessageManager;
import servises.parameterManager.ParameterManager;

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

        if (ParameterManager.isAllEmpty(sDate, sTime, theme, city, street, building, room, email)) {
            request.setAttribute("errorEmptyForm", MessageManager.getProperty("emptyForm"));
            return page;
        }

        Date date = DateTimeManager.fromStringToSqlDate(sDate);

        if (new java.util.Date().getTime() > date.getTime()) {
            request.setAttribute("errorDate", MessageManager.getProperty("incorrectDate"));
            return page;
        }

        Address address = new Address(city, street, building, room);
        if (!ParameterManager.isAddressCorrect(address)) {
            request.setAttribute("errorAddress", MessageManager.getProperty("addressIncorrect"));
            return page;
        }

        if (!ParameterManager.isThemeCorrect(theme)) {
            request.setAttribute("errorTheme", MessageManager.getProperty("themeIncorrect"));
            return page;
        }

        UserDao userDao = DaoFactory.getUserDao();
        Speaker speaker = userDao.getSpeakerByEmail(email);
        userDao.closeConnection();

        if (speaker == null) {
            request.setAttribute("errorSpeakerNotExists", MessageManager.getProperty("speakerNotExists"));
            return page;
        }
        Time time = DateTimeManager.fromStringToTime(sTime);
        Report report = new Report(theme, address, date, time, speaker);

        ReportDao reportDao = DaoFactory.getReportDao();
        int result = reportDao.addReport(report);
        reportDao.closeConnection();

        if (result != 0) {
            request.setAttribute("successfulChanges", MessageManager.getProperty("successfulChanges"));
            MailManager.notifySpeakerAppointment(speaker, report);
        }

        return page;
    }
}
