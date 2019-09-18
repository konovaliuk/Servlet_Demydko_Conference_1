package commands.commandHelpers;

import entity.Address;
import entity.Report;
import entity.Speaker;
import servises.dateTimeManager.DateTimeManager;
import servises.mailManager.MailManager;
import servises.parameterManager.ParameterManager;
import servises.reportManager.ReportManager;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

public class EditReportHelper implements CommandHelper {

    private String index;
    private String sDate;
    private String sTime;
    private String city;
    private String street;
    private String building;
    private String room;
    private List<Report> reportList;

    public EditReportHelper(String index, String sDate, String sTime,
                            String city, String street, String building, String room, List<Report> reportList) {
        this.index = index;
        this.sDate = sDate;
        this.sTime = sTime;
        this.city = city;
        this.street = street;
        this.building = building;
        this.room = room;
        this.reportList = reportList;
    }

    @Override
    public String handle() {
        ParameterManager parameterManager = new ParameterManager();
        if (parameterManager.isEmpty(sDate, sTime, city, street, building, room)) {
            return "errorEmptyForm";
        }
        Address address = new Address(city, street, building, room);
        if (!parameterManager.isAddressCorrect(address)) {
            return "errorAddress";
        }
        Report report = reportList.get(Integer.parseInt(index));
        DateTimeManager dateTimeManager = new DateTimeManager();
        Date date = dateTimeManager.fromStringToSqlDate(sDate);
        Time time = dateTimeManager.fromStringToTime(sTime);

        report.setTime(time);
        report.setDate(date);
        report.setAddress(address);
        ReportManager reportManager = new ReportManager();
        int result = reportManager.updateReport(report);
        MailManager mail = new MailManager();
        if (result != 0) {
            Speaker speaker = report.getSpeaker();
            mail.notifySpeakerAppointment(speaker, report);
        }
        return "success";
    }
}
