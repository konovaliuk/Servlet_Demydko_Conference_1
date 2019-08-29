package commands.impl;

import commands.Command;
import databaseLogic.dao.ReportDao;
import databaseLogic.dao.UserDao;
import databaseLogic.factory.DaoFactory;
import entity.Address;
import entity.Speaker;
import servises.configManager.ConfigManager;
import servises.dateTimeManager.DateTimeManager;
import servises.mailManager.MailThread;
import servises.messageManager.BuildMessageManager;
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

        if (ParameterManager.isEmpty(sDate, sTime, theme, city, street, building, room, email)) {
            request.setAttribute("errorEmptyForm", MessageManager.getProperty("emptyForm"));
            return page;
        }

        Date date = DateTimeManager.fromStringToSqlDate(sDate);

        if (new java.util.Date().getTime() > date.getTime()) {
            request.setAttribute("errorDate", MessageManager.getProperty("incorrectDate"));
            return page;
        }

        Address address = new Address(city, street, building, room);
        Time time = DateTimeManager.fromStringToTime(sTime);

        UserDao userDao = DaoFactory.getUserDao();
        Speaker speaker = userDao.getSpeakerByEmail(email);
        userDao.closeConnection();

        if (speaker == null) {
            request.setAttribute("errorSpeakerNotExists", MessageManager.getProperty("speakerNotExists"));
            return page;
        }

        ReportDao reportDao = DaoFactory.getReportDao();
        int result = reportDao.addReport(theme, address, date, time, speaker);
        reportDao.closeConnection();

        if (result != 0) {
            request.setAttribute("successfulChanges", MessageManager.getProperty("successfulChanges"));
            MailThread mailOperator = new MailThread(email, MessageManager.getProperty("conferenceAppointment"),
                    BuildMessageManager.buildMessage(MessageManager.getProperty("speakerAppointment"),
                            speaker.getName(), theme, sDate, DateTimeManager.fromTimeToString(time))+"\n"+
                    BuildMessageManager.buildMessage(MessageManager.getProperty("location"),
                            address.getCity(),address.getStreet(),address.getBuilding(),address.getRoom()));
            mailOperator.start();
        }

        return page;
    }
}
