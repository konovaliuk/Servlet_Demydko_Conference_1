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
import servises.mailManager.MailThread;
import servises.messageManager.BuildMessageManager;
import servises.messageManager.MessageManager;
import servises.parameterManager.ParameterManager;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

public class UpdateReportCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) {
        String page = ConfigManager.getProperty("updateReport");

        String index = request.getParameter("index");
        String theme = request.getParameter("theme");
        String sDate = request.getParameter("date");
        String sTime = request.getParameter("time");
        String city = request.getParameter("city");
        String street = request.getParameter("street");
        String building = request.getParameter("building");
        String room = request.getParameter("room");
        String email = request.getParameter("speakerEmail");

        request.setAttribute("reportIndex", index);

        if (ParameterManager.isEmpty(theme, sDate, sTime, city, street, building, room, email)) {
            request.setAttribute("noActionDone", MessageManager.getProperty("noAction"));
            return page;
        }

        List<Report> reportList = (List) request.getSession().getAttribute("reportList");
        Report report = reportList.get(Integer.parseInt(index));

        Speaker speaker;
        Speaker oldSpeaker = null;

        if (!email.isEmpty()) {
            UserDao userDao = DaoFactory.getUserDao();
            speaker = userDao.getSpeakerByEmail(email);
            userDao.closeConnection();
            if (speaker == null) {
                request.setAttribute("errorSpeakerNotExists", MessageManager.getProperty("speakerNotExists"));
                return page;
            }
            oldSpeaker = report.getSpeaker();
        } else {
            speaker = report.getSpeaker();
        }

        String oldDate = DateTimeManager.fromDateToString(report.getDate());
        String oldTime = DateTimeManager.fromTimeToString(report.getTime());
        String oldTheme = report.getName();
        Address oldAddress = report.getAddress();

        theme = theme.isEmpty() ? report.getName() : theme;
        city = city.isEmpty() ? oldAddress.getCity() : city;
        street = street.isEmpty() ? oldAddress.getStreet() : street;
        building = building.isEmpty() ? oldAddress.getBuilding() : building;
        room = room.isEmpty() ? oldAddress.getRoom() : room;
        Address address = new Address(city, street, building, room);
        Date date = sDate.isEmpty() ? DateTimeManager.fromUtilDateToSqlDate(report.getDate()) : DateTimeManager.fromStringToSqlDate(sDate);
        Time time = sTime.isEmpty() ? report.getTime() : DateTimeManager.fromStringToTime(sTime);

        ReportDao reportDao = DaoFactory.getReportDao();
        int result = reportDao.updateReport(report.getId(), theme, address, date, time, speaker);
        reportDao.closeConnection();

        long reportId = report.getId();
        report = new Report();
        report.setId(reportId);
        report.setName(theme);
        report.setAddress(address);
        report.setSpeaker(speaker);
        report.setDate(date);
        report.setTime(time);

        if (result != 0) {
            request.setAttribute("successfulChanges", MessageManager.getProperty("successfulChanges"));
            reportList.set(Integer.parseInt(index), report);
            request.getSession().setAttribute("reportList", reportList);
        }

        if (!email.isEmpty()) {
            MailThread mailOperator = new MailThread(email, MessageManager.getProperty("conferenceAppointment"),
                    BuildMessageManager.buildMessage(MessageManager.getProperty("speakerAppointment"),
                            speaker.getName(), theme, DateTimeManager.fromDateToString(date), DateTimeManager.fromTimeToString(time))
                            + "\n" +
                            BuildMessageManager.buildMessage(MessageManager.getProperty("location"),
                                    address.getCity(), address.getStreet(), address.getBuilding(), address.getRoom()));
            mailOperator.start();
            mailOperator = new MailThread(oldSpeaker.getEmail(), MessageManager.getProperty("dismissFromConference"),
                    BuildMessageManager.buildMessage(MessageManager.getProperty("dismissMessage"),
                            oldSpeaker.getName(), oldTheme, oldDate, oldTime)
                            + "\n" +
                            BuildMessageManager.buildMessage(MessageManager.getProperty("location"),
                                    address.getCity(), address.getStreet(), address.getBuilding(), address.getRoom()));
            mailOperator.start();
        } else {
            MailThread mailOperator = new MailThread(speaker.getEmail(), MessageManager.getProperty("changedConference"),
                    BuildMessageManager.buildMessage(MessageManager.getProperty("changeInConference"),
                            speaker.getName(), oldTheme, oldDate, oldTime) + "\n"
                            +
                            BuildMessageManager.buildMessage(MessageManager.getProperty("location"),
                                    oldAddress.getCity(), oldAddress.getStreet(),
                                    oldAddress.getBuilding(), oldAddress.getRoom() + "\n\n")
                            +
                            BuildMessageManager.buildMessage(MessageManager.getProperty("newConference"),
                                    theme, DateTimeManager.fromDateToString(date), DateTimeManager.fromTimeToString(time)) + "\n"
                            +
                            BuildMessageManager.buildMessage(MessageManager.getProperty("location"),
                                    address.getCity(), address.getStreet(),
                                    address.getBuilding(), address.getRoom()));
            mailOperator.start();
        }

        return page;
    }
}
