package databaseLogic.dao;

import entity.Address;
import entity.Report;
import entity.Speaker;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

public interface ReportDao {

    int addReport(String name, Address address, Date date, Time time, Speaker speaker);
    int addReport(String name,Speaker speaker);

    List<Report> getFutureConference();
    List<Report> getOfferedConference();

    int updateReport(long reportId, String name, Address address, Date date, Time time, Speaker speaker);

    void closeConnection();
}
