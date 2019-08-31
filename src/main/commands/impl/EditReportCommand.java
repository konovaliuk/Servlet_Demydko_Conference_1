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
import servises.mailManager.MailThread;
import servises.messageManager.BuildMessageManager;
import servises.messageManager.MessageManager;
import servises.parameterManager.ParameterManager;

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

        if (ParameterManager.isEmpty(sDate, sTime, city, street, building, room)) {
            request.setAttribute("errorEmptyForm", MessageManager.getProperty("emptyForm"));
            return page;
        }

        List<Report> reportList = (List) request.getSession().getAttribute("offeredReportList");
        Report report = reportList.get(Integer.parseInt(index));


        Address address = new Address(city, street, building, room);
        Date date = DateTimeManager.fromStringToSqlDate(sDate);
        Time time = DateTimeManager.fromStringToTime(sTime);

        report.setTime(time);
        report.setDate(date);
        report.setAddress(address);

        ReportDao reportDao = DaoFactory.getReportDao();
        int result = reportDao.updateReport(report.getId(), report);
        reportDao.closeConnection();

        if (result != 0) {
            request.setAttribute("successfulChanges", MessageManager.getProperty("successfulChanges"));
            Speaker speaker = report.getSpeaker();
            MailManager.notifySpeakerAppointment(speaker, report);
        }

        return page;
    }
}
