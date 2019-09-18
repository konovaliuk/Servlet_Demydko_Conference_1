package commands.impl;

import commands.Command;
import commands.commandHelpers.EditReportHelper;
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

        String index = request.getParameter("index");
        String sDate = request.getParameter("date");
        String sTime = request.getParameter("time");
        String city = request.getParameter("city");
        String street = request.getParameter("street");
        String building = request.getParameter("building");
        String room = request.getParameter("room");
        List<Report> reportList = (List) request.getSession().getAttribute("offeredReportList");


        request.setAttribute("reportIndex", index);

        EditReportHelper helper = new EditReportHelper(index, sDate, sTime, city, street, building, room, reportList);
        String result = helper.handle();
        MessageManager message = new MessageManager();
        request.setAttribute(result, message.getProperty(result));
        return ConfigManager.getProperty("editReport");
    }
}
