package commands.impl;

import commands.Command;
import databaseLogic.dao.RegisterDao;
import databaseLogic.dao.ReportDao;
import databaseLogic.factory.DaoFactory;
import entity.Report;
import entity.User;
import servises.configManager.ConfigManager;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FutureReportsCommand implements Command {


    @Override
    public String execute(HttpServletRequest request) {
//        ReportDao reportDao = DaoFactory.getReportDao();
////        List<Report> list = reportDao.getFutureConference();
////        reportDao.closeConnection();
////        User user = (User) request.getSession().getAttribute("user");
////        if (user.getPosition().equals("User")) {
////            RegisterDao registerDao = DaoFactory.getRegisterDao();
////            List<Long> registerList = registerDao.getReportsIdByUserId(user.getId());
////            registerDao.closeConnection();
////            for (Long id : registerList) {
////                for (Report report : list) {
////                    if (report.getId() == id) {
////                        report.setIsUserRegistered(true);
////                    }
////                }
////            }
////        }
////        request.getSession().setAttribute("reportList", list);


        String requestOffset = request.getParameter("offset");
        String requestMaxCount = request.getParameter("maxCount");

        int offset;
        int maxCount;

        List<Report> reportList;
        if (requestOffset == null && requestMaxCount == null) {
            String sessionMaxCount = (String) request.getSession().getAttribute("maxCount");
//            if (sessionMaxCount == null) {
//                maxCount = 5;
//            } else {
//                maxCount = Integer.parseInt(sessionMaxCount);
//            }

            maxCount = (sessionMaxCount != null) ? Integer.parseInt(sessionMaxCount) : 5;    // todo
            Integer sessionOffset = (Integer) request.getSession().getAttribute("offset");
//            if (sessionOffset != null) {
//                offset = sessionOffset;
//            } else {
//                offset = 0;
//            }
            offset = (sessionOffset != null) ? sessionOffset : 0;                        //todo
            ReportDao reportDao = DaoFactory.getReportDao();
            int result = reportDao.getCountReports();
            reportList = reportDao.getFutureConference(offset, maxCount);
            reportDao.closeConnection();
            double buttons = result / (double) maxCount;
            buttons = Math.ceil(buttons);
            List<Integer> list = new ArrayList<>();
            for (int i = 1; i <= buttons; i++) {
                list.add(i);
            }
            request.getSession().setAttribute("buttons", list);
        } else {
            if (requestMaxCount == null) {
                String sessionMaxCount = (String) request.getSession().getAttribute("maxCount");
                if (sessionMaxCount == null) {
                    maxCount = 5;
                } else {
                    maxCount = Integer.parseInt(sessionMaxCount);
                }
            } else {
                maxCount = Integer.parseInt(requestMaxCount);
                request.getSession().setAttribute("maxCount", requestMaxCount);
                ReportDao reportDao = DaoFactory.getReportDao();
                int result = reportDao.getCountReports();
                reportDao.closeConnection();
                double buttons = result / (double) maxCount;
                buttons = Math.ceil(buttons);
                List<Integer> list = new ArrayList<>();
                for (int i = 1; i <= buttons; i++) {
                    list.add(i);
                }
                request.getSession().setAttribute("buttons", list);
            }
            Integer sessionOffset = (Integer) request.getSession().getAttribute("offset");
            if (requestOffset != null) {
                offset = Integer.parseInt(requestOffset) * maxCount - maxCount;
            } else if (sessionOffset != null) {
                offset = sessionOffset;
            } else {
                offset = 0;
            }
            request.getSession().setAttribute("offset", offset);
            ReportDao reportDao = DaoFactory.getReportDao();
            reportList = reportDao.getFutureConference(offset, maxCount);
            reportDao.closeConnection();
        }

        User user = (User) request.getSession().getAttribute("user");
        if (user.getPosition().equals("User")) {
            RegisterDao registerDao = DaoFactory.getRegisterDao();
            List<Long> registerList = registerDao.getReportsIdByUserId(user.getId());
            registerDao.closeConnection();
            for (Long id : registerList) {
                for (Report report : reportList) {
                    if (report.getId() == id) {
                        report.setIsUserRegistered(true);
                    }
                }
            }
        }

        request.getSession().setAttribute("reportList", reportList);


        return ConfigManager.getProperty("futureReports");
    }
}
