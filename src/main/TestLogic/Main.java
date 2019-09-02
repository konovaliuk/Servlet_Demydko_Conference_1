package TestLogic;

import databaseLogic.dao.RegisterDao;
import databaseLogic.dao.UserDao;
import databaseLogic.factory.DaoFactory;
import entity.Address;
import entity.Speaker;
import entity.User;
import servises.parameterManager.ParameterManager;
import servises.userManager.UserManager;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {
    public static void main(String[] args) throws ParseException {
        Speaker speaker = new Speaker();
        speaker.setRating(1);
        System.out.println(UserManager.setSpeakerBonuses(100, speaker));


//        UserDao userDao = DaoFactory.getUserDao();
       // Speaker speaker = userDao.getSpeakerByEmail("zelen@ukr.net");
//        Speaker speaker = userDao.getSpeakerByEmail("sfgsdg@ukr.net");
//        userDao.closeConnection();
//        System.out.println(speaker);
      //  command=addreport&theme=hack2&date=2019-10-11&time=12:00
      //  System.out.println(DateTimeManager.fromStringToSqlDate("2019-10-08"));

//        String timeStamp = new java.sql.Time(System.currentTimeMillis()).toString();
//        System.out.println(timeStamp);


        //   System.out.println(DateTimeManager.fromSqlDateToUtilDate(new java.sql.Date(new Date().getTime())));


//        java.util.Date uDate = new java.util.Date();
//        System.out.println("Time in java.util.Date is : " + uDate);
//
//        java.sql.Date sDate = convertUtilToSql(uDate);
//        System.out.println("Time in java.sql.Date is : " + sDate);
//        DateFormat df = new SimpleDateFormat("dd/MM/YYYY - hh:mm:ss");
//        System.out.println("Using a dateFormat date is : " + df.format(uDate));
    }

    private static java.sql.Date convertUtilToSql(java.util.Date uDate) {
        java.sql.Date sDate = new java.sql.Date(uDate.getTime());
        System.out.println(sDate);
        // sDate.setTime();
        return sDate;
    }

    private static void dateExample() throws ParseException {
       // SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");//задаю формат даты
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");//задаю формат даты
//        String dateInString = "29-11-2015";//создаю строку по заданному формату
        String dateInString = "2019-08-29";//создаю строку по заданному формату
        Date date = formatter.parse(dateInString);//создаю дату через
        java.sql.Date sDate = new java.sql.Date(date.getTime());
        System.out.println("sDate: " + sDate);
        System.out.println(date);
        System.out.println(formatter.format(date)); //получаем sql дату, но уже в формате 2011-05-05 (yyyy-MM-dd)
        DateFormat df = new SimpleDateFormat("dd/MM/YYYY");
        System.out.println("Using a dateFormat date is : " + df.format(date));
    }

    static void addUser() {
        User user = new User();
        user.setName("Вова");
        user.setSurname("Speak");
        user.setPassword("zelen");
        user.setEmail("zelen@ukr.net");
        user.setPosition("Speaker");

        UserDao userDao = DaoFactory.getUserDao();
        userDao.addUser(user);
        userDao.closeConnection();
    }
}
