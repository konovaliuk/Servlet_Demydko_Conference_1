package servises.reportManager;

import databaseLogic.connection.DataSourceConference;
import databaseLogic.dao.ReportDao;
import databaseLogic.factory.DaoFactory;
import entity.Report;
import entity.Speaker;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ReportManager {
    ReportDao reportDao;

    public List<Report> getFutureConference(int offset, int maxCount) {
        reportDao = DaoFactory.getReportDao();
        List<Report> reportList = reportDao.getFutureConference(offset, maxCount);
        reportDao.closeConnection();
        return reportList;
    }

    public int getCountOfFutureReports() {
        reportDao = DaoFactory.getReportDao();
        int result = reportDao.getCountOfFutureReports();
        reportDao.closeConnection();
        return result;
    }

    public int getCountOfPastReports() {
        reportDao = DaoFactory.getReportDao();
        int result = reportDao.getCountOfPastReports();
        reportDao.closeConnection();
        return result;
    }

    public List<Report> getPastConference(int offset, int maxCount) {
        reportDao = DaoFactory.getReportDao();
        List<Report> reportList = reportDao.getPastConference(offset, maxCount);
        reportDao.closeConnection();
        return reportList;
    }

    public int addReport(Report report) {
        reportDao = DaoFactory.getReportDao();
        int result = reportDao.addReport(report);
        reportDao.closeConnection();
        return result;
    }

//    public int addReport(Report report) {
//        DataSourceConference dataSource = DataSourceConference.getInstance();
//        Connection connection = dataSource.getConnection();
//        int result = 0;
//        try {
//            connection.setAutoCommit(false);
//            reportDao = DaoFactory.getReportDao(connection);
//           result = reportDao.addReport(report);
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        dataSource.closeConnection();
//        return result;
//    }



    public int addReport(String reportName, Speaker speaker) {
        reportDao = DaoFactory.getReportDao();
        int result = reportDao.addReport(reportName, speaker);
        reportDao.closeConnection();
        return result;
    }

    public void deleteReport(List<Report> reports, Long reportId) {
        boolean isPresent = false;
        for (Report report : reports) {
            if (report.getId().equals(reportId)) {
                reports.remove(report);
                isPresent = true;
                break;
            }
        }
        if (isPresent) {
            ReportDao reportDao = DaoFactory.getReportDao();
            reportDao.deleteReport(reportId);
            reportDao.closeConnection();
        }
    }

    public int updateReport(Report report) {
        reportDao = DaoFactory.getReportDao();
        int result = reportDao.updateReport(report);
        reportDao.closeConnection();
        return result;
    }

    public List<Report> getOfferedConference() {
        reportDao = DaoFactory.getReportDao();
        List<Report> offeredConference = reportDao.getOfferedConference();
        reportDao.closeConnection();
        return offeredConference;
    }
}
