package commands.impl;

import commands.Command;
import databaseLogic.dao.ReportDao;
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

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

public class EditReportCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        String page = ConfigManager.getProperty("editReport");

        String index = request.getParameter("index");
        String sDate = request.getParameter("date");
        String sTime = request.getParameter("time");
        String city = request.getParameter("city");
        String street = request.getParameter("street");
        String building = request.getParameter("building");
        String room = request.getParameter("room");

        request.setAttribute("reportIndex", index);

        ParameterManager parameterManager = new ParameterManager();
        MessageManager message = new MessageManager();

        if (parameterManager.isEmpty(sDate, sTime, city, street, building, room)) {
            request.setAttribute("errorEmptyForm", message.getProperty("errorEmptyForm"));
            return page;
        }

        Address address = new Address(city, street, building, room);
        if (!parameterManager.isAddressCorrect(address)) {
            request.setAttribute("errorAddress", message.getProperty("addressIncorrect"));
            return page;
        }

        List<Report> reportList = (List) request.getSession().getAttribute("offeredReportList");
        Report report = reportList.get(Integer.parseInt(index));

        DateTimeManager dateTimeManager = new DateTimeManager();
        Date date = dateTimeManager.fromStringToSqlDate(sDate);
        Time time = dateTimeManager.fromStringToTime(sTime);

        report.setTime(time);
        report.setDate(date);
        report.setAddress(address);

        ReportManager reportManager = new ReportManager();
        int result = reportManager.updateReport(report);

//        ReportDao reportDao = DaoFactory.getReportDao();
//        int result = reportDao.updateReport(report);
//        reportDao.closeConnection();

        MailManager mail = new MailManager();
        if (result != 0) {
            request.setAttribute("successfulChanges", message.getProperty("successfulChanges"));
            Speaker speaker = report.getSpeaker();
            mail.notifySpeakerAppointment(speaker, report);
        }

        return page;
    }
}
