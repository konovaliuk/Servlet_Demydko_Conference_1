package databaseLogic.dao;

import entity.Address;
import entity.Report;
import entity.Speaker;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

public interface ReportDao {

    int addReport(Report report);
    int addReport(String name,Speaker speaker);

    List<Report> getFutureConference(int offset,int maxCount);
    List<Report> getOfferedConference();
    List<Report> getPastConference();

    int getCountReports();                    // todo

    int updateReport(Report report);

    int deleteReport(Long reportId);

    void closeConnection();
}
