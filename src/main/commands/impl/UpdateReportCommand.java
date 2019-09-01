package commands.impl;

import commands.Command;
import databaseLogic.dao.RegisterDao;
import databaseLogic.dao.ReportDao;
import databaseLogic.dao.UserDao;
import databaseLogic.factory.DaoFactory;
import entity.Address;
import entity.Report;
import entity.Speaker;
import entity.User;
import servises.configManager.ConfigManager;
import servises.dateTimeManager.DateTimeManager;
import servises.mailManager.MailManager;
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

        if (ParameterManager.isAllEmpty(theme, sDate, sTime, city, street, building, room, email)) {
            request.setAttribute("noActionDone", MessageManager.getProperty("noAction"));
            return page;
        }

        List<Report> reportList = (List) request.getSession().getAttribute("reportList");
        Report oldReport = reportList.get(Integer.parseInt(index));

        Speaker newSpeaker;
        if (!email.isEmpty()) {
            UserDao userDao = DaoFactory.getUserDao();
            newSpeaker = userDao.getSpeakerByEmail(email);
            userDao.closeConnection();
            if (newSpeaker == null) {
                request.setAttribute("errorSpeakerNotExists", MessageManager.getProperty("speakerNotExists"));
                return page;
            }
        } else {
            newSpeaker = oldReport.getSpeaker();
        }

        Speaker oldSpeaker = oldReport.getSpeaker();
        Address oldAddress = oldReport.getAddress();

        String newTheme = theme.isEmpty() ? oldReport.getName() : theme;
        String newCity = city.isEmpty() ? oldAddress.getCity() : city;
        String newStreet = street.isEmpty() ? oldAddress.getStreet() : street;
        String newBuilding = building.isEmpty() ? oldAddress.getBuilding() : building;
        String newRoom = room.isEmpty() ? oldAddress.getRoom() : room;
        Address newAddress = new Address(newCity, newStreet, newBuilding, newRoom);
        Date date = sDate.isEmpty() ? DateTimeManager.fromUtilDateToSqlDate(oldReport.getDate()) : DateTimeManager.fromStringToSqlDate(sDate);
        Time time = sTime.isEmpty() ? oldReport.getTime() : DateTimeManager.fromStringToTime(sTime);


        if (new java.util.Date().getTime() > date.getTime()) {
            request.setAttribute("errorDate", MessageManager.getProperty("incorrectDate"));
            return page;
        }

        if (!ParameterManager.isAddressCorrect(newAddress)) {
            request.setAttribute("errorAddress", MessageManager.getProperty("addressIncorrect"));
            return page;
        }

        if (!ParameterManager.isThemeCorrect(newTheme)) {
            request.setAttribute("errorTheme", MessageManager.getProperty("themeIncorrect"));
            return page;
        }

        long reportId = oldReport.getId();
        Report newReport = new Report();
        newReport.setId(reportId);
        newReport.setName(newTheme);
        newReport.setAddress(newAddress);
        newReport.setSpeaker(newSpeaker);
        newReport.setDate(date);
        newReport.setTime(time);

        ReportDao reportDao = DaoFactory.getReportDao();
        int result = reportDao.updateReport(newReport);
        reportDao.closeConnection();

        if (result != 0) {
            request.setAttribute("successfulChanges", MessageManager.getProperty("successfulChanges"));
            reportList.set(Integer.parseInt(index), newReport);
            request.getSession().setAttribute("reportList", reportList);
        }

        RegisterDao registerDao = DaoFactory.getRegisterDao();
        List<User> userList = registerDao.getAllRegisteredUsers(reportId);
        registerDao.closeConnection();

        if (!email.isEmpty() && ParameterManager.isAllEmpty(theme, sDate, sTime, city, street, building, room)) {
            MailManager.notifySpeakerAppointment(newSpeaker, oldReport);
            MailManager.notifySpeakerDismiss(oldSpeaker, oldReport);
        } else if (!email.isEmpty() && !ParameterManager.isAllEmpty(theme, sDate, sTime, city, street, building, room)) {
            MailManager.notifySpeakerAppointment(newSpeaker, newReport);
            MailManager.notifySpeakerDismiss(oldSpeaker, oldReport);
            MailManager.notifyChangeConference(newReport, oldReport, userList);
        } else {
            userList.add(newSpeaker);
            MailManager.notifyChangeConference(newReport, oldReport, userList);
        }

        return page;
    }
}
