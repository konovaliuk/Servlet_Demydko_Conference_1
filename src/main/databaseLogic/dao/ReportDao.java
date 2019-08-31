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

    List<Report> getFutureConference();
    List<Report> getOfferedConference();

    int updateReport(long reportId, Report report);

    int deleteReport(long reportId);

    void closeConnection();
}
