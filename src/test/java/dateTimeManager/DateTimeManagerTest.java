package dateTimeManager;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import servises.dateTimeManager.DateTimeManager;

import java.sql.Date;
import java.sql.Time;

public class DateTimeManagerTest {
    private static DateTimeManager dtm;

    @BeforeClass
    public static void init() {
        dtm = new DateTimeManager();
    }

    @AfterClass
    public static void clear() {
        dtm = null;
    }

    @Test
    public void fromStringToSqlDateTest() {
        String sDate = "2019-09-08";
        Date sqlDate = dtm.fromStringToSqlDate(sDate);
        Assert.assertNotNull(sqlDate);
    }

    @Test
    public void fromUtilDateToSqlDateTest() {
        java.util.Date uDate = new java.util.Date();
        Date sqlDate = dtm.fromUtilDateToSqlDate(uDate);
        Assert.assertNotNull(sqlDate);
    }

    @Test
    public void fromDateToStringTest() {
        java.util.Date uDate = new java.util.Date();
        String sDate = dtm.fromDateToString(uDate);
        Assert.assertNotNull(sDate);
    }

    @Test
    public void fromStringToTimeTest() {
        String sTime = "23:34";
        Time time = dtm.fromStringToTime(sTime);
        Assert.assertNotNull(time);
    }

    @Test
    public void fromTimeToStringTest() {
        Time time = new Time(new java.util.Date().getTime());
        String sTime = dtm.fromTimeToString(time);
        Assert.assertNotNull(sTime);
    }
}
