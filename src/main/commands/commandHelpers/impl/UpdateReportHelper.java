package commands.commandHelpers.impl;


import commands.commandHelpers.CommandHelper;
import entity.Address;
import entity.Report;
import entity.Speaker;
import entity.User;
import org.apache.log4j.Logger;
import servises.dateTimeManager.DateTimeManager;
import servises.mailManager.MailManager;
import servises.parameterManager.ParameterManager;
import servises.registerManager.RegisterManager;
import servises.reportManager.ReportManager;
import servises.spaekerManager.SpeakerManager;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

public class UpdateReportHelper implements CommandHelper {
    private Logger logger = Logger.getLogger(UpdateReportHelper.class);
    private String index;
    private String theme;
    private String sDate;
    private String sTime;
    private String city;
    private String street;
    private String building;
    private String room;
    private String email;
    private List<Report> reportList;

    public UpdateReportHelper(String index, String theme,
                              String sDate, String sTime, String city,
                              String street, String building, String room,
                              String email, List<Report> reportList) {
        this.index = index;
        this.theme = theme;
        this.sDate = sDate;
        this.sTime = sTime;
        this.city = city;
        this.street = street;
        this.building = building;
        this.room = room;
        this.email = email;
        this.reportList = reportList;
    }

    @Override
    public String handle() {
        ParameterManager parameterManager = new ParameterManager();
        DateTimeManager dateTimeManager = new DateTimeManager();
        SpeakerManager speakerManager = new SpeakerManager();
        MailManager mail = new MailManager();

        if (parameterManager.isAllEmpty(theme, sDate, sTime, city, street, building, room, email)) {
            logger.info("No action done");
            return "noActionDone";
        }

        Report oldReport = reportList.get(Integer.parseInt(index));

        Speaker newSpeaker;
        if (!email.isEmpty()) {
            newSpeaker = speakerManager.getSpeakerByEmail(email);
            if (newSpeaker == null) {
                logger.info("Speaker with such " + email + " email does not exist");
                return "errorSpeakerNotExists";
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
        Date date = sDate.isEmpty() ? dateTimeManager.fromUtilDateToSqlDate(oldReport.getDate()) : dateTimeManager.fromStringToSqlDate(sDate);
        Time time = sTime.isEmpty() ? oldReport.getTime() : dateTimeManager.fromStringToTime(sTime);

        if (new java.util.Date().getTime() > date.getTime()) {
            logger.info("The date was imputed incorrectly");
            return "errorDate";
        }

        if (!parameterManager.isAddressCorrect(newAddress)) {
            logger.info("Address was imputed incorrectly");
            return "errorAddress";
        }

        if (!parameterManager.isThemeCorrect(newTheme)) {
            logger.info("Selected incorrect name of theme: " + theme);
            return "errorTheme";
        }

        Long reportId = oldReport.getId();
        Report newReport = new Report();
        newReport.setId(reportId);
        newReport.setName(newTheme);
        newReport.setAddress(newAddress);
        newReport.setSpeaker(newSpeaker);
        newReport.setDate(date);
        newReport.setTime(time);

        ReportManager reportManager = new ReportManager();
        int result = reportManager.updateReport(newReport);

        if (result != 0) {
            reportList.set(Integer.parseInt(index), newReport);
        } else {
            logger.info("No changes made");
            return "NoChangesMade";
        }

        RegisterManager registerManager = new RegisterManager();
        List<User> userList = registerManager.getAllRegisteredUsers(reportId);

        if (!email.isEmpty() && parameterManager.isAllEmpty(theme, sDate, sTime, city, street, building, room)) {
            mail.notifySpeakerAppointment(newSpeaker, oldReport);
            mail.notifySpeakerDismiss(oldSpeaker, oldReport);
        } else if (!email.isEmpty() && !parameterManager.isAllEmpty(theme, sDate, sTime, city, street, building, room)) {
            mail.notifySpeakerAppointment(newSpeaker, newReport);
            mail.notifySpeakerDismiss(oldSpeaker, oldReport);
            mail.notifyChangeConference(newReport, oldReport, userList);
        } else {
            userList.add(newSpeaker);
            mail.notifyChangeConference(newReport, oldReport, userList);
        }
        logger.info("Report with id: " + reportId + "  was successfully updated ");
        return "successfulChanges";
    }
}
