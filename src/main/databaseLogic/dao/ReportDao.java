package databaseLogic.dao;

import entity.Address;
import entity.Report;
import entity.Speaker;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

public interface ReportDao {

    Long addReport(Report report);

    int addReport(String reportName, Speaker speaker);

    List<Report> getFutureConference(int offset, int maxCount);

    List<Report> getPastConference(int offset, int maxCount);

    List<Report> getOfferedConference(int offset, int maxCount);

//    List<Report> getPastReports();

    void setAddressForReport(Long addressId, Long reportId);

    int getCountOfFutureReports();                    // todo

    int getCountOfPastReports();

    int getCountOfOfferedReports();

    int updateReport(Report report);

    int deleteReport(Long reportId);

    void closeConnection();
}
