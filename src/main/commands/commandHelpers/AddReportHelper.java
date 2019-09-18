package commands.commandHelpers;

import entity.Address;
import entity.Report;
import entity.Speaker;
import servises.dateTimeManager.DateTimeManager;
import servises.mailManager.MailManager;
import servises.parameterManager.ParameterManager;
import servises.reportManager.ReportManager;
import servises.spaekerManager.SpeakerManager;

import java.sql.Date;
import java.sql.Time;

public class AddReportHelper implements CommandHelper {
    private String sDate;
    private String sTime;
    private String theme;
    private String city;
    private String street;
    private String building;
    private String room;
    private String email;

    public AddReportHelper(String sDate, String sTime,
                           String theme, String city, String street,
                           String building, String room, String email) {
        this.sDate = sDate;
        this.sTime = sTime;
        this.theme = theme;
        this.city = city;
        this.street = street;
        this.building = building;
        this.room = room;
        this.email = email;
    }

    @Override
    public String handle() {
        ParameterManager parameterManager = new ParameterManager();
        if (parameterManager.isEmpty(sDate, sTime, theme, city, street, building, room, email)) {
            return "errorEmptyForm";
        }
        DateTimeManager dateTimeManager = new DateTimeManager();
        Date date = dateTimeManager.fromStringToSqlDate(sDate);
        if (new java.util.Date().getTime() > date.getTime()) {
            return "errorDate";
        }
        Address address = new Address(city, street, building, room);
        if (!parameterManager.isAddressCorrect(address)) {
            return "errorAddress";
        }
        if (!parameterManager.isThemeCorrect(theme)) {
            return "errorTheme";
        }
        SpeakerManager speakerManager = new SpeakerManager();
        Speaker speaker = speakerManager.getSpeakerByEmail(email);
        if (speaker == null) {
            return "errorSpeakerNotExists";
        }
        Time time = dateTimeManager.fromStringToTime(sTime);
        Report report = new Report(theme, address, date, time, speaker);
        ReportManager reportManager = new ReportManager();
        int result = reportManager.addReport(report);
        if (result != 0) {
            MailManager mail = new MailManager();
            mail.notifySpeakerAppointment(speaker, report);
        }
        return "successfulChanges";
    }
}
