package servises.mailManager;

import entity.Report;
import entity.Speaker;
import entity.User;
import servises.dateTimeManager.DateTimeManager;
import servises.messageManager.MessageManager;

import java.util.List;
import java.util.ResourceBundle;

public class MailManager {
    private final static ResourceBundle resourceBundle = ResourceBundle.getBundle("mail");
    private static DateTimeManager dtm = new DateTimeManager();
    private MailManager() {
    }

    public static String getProperty(String key) {
        return resourceBundle.getString(key);
    }

    public static void notifySpeakerAppointment(Speaker speaker, Report report) {
        MailThread mailOperator = new MailThread(speaker.getEmail(), MessageManager.getProperty("conferenceAppointment"),
                buildMessage(MessageManager.getProperty("speakerAppointment"),
                        speaker.getName(), report.getName(), dtm.fromDateToString(report.getDate()),
                        dtm.fromTimeToString(report.getTime()))
                        + "\n" +
                        buildMessage(MessageManager.getProperty("location"),
                                report.getAddress().getCity(), report.getAddress().getStreet(),
                                report.getAddress().getBuilding(), report.getAddress().getRoom()));
        mailOperator.start();
    }

    public static void notifySpeakerDismiss(Speaker speaker, Report report) {
        MailThread mailOperator = new MailThread(speaker.getEmail(), MessageManager.getProperty("dismissFromConference"),
                buildMessage(MessageManager.getProperty("dismissMessage"),
                        speaker.getName(), report.getName(), dtm.fromDateToString(report.getDate()),
                        dtm.fromTimeToString(report.getTime())
                                + "\n" +
                                buildMessage(MessageManager.getProperty("location"),
                                        report.getAddress().getCity(), report.getAddress().getStreet(),
                                        report.getAddress().getBuilding(), report.getAddress().getRoom())));
        mailOperator.start();
    }

    public static void notifyChangeConference(Report newReport, Report oldReport, List<User> userList) {
        for (User user : userList) {
            MailThread mailOperator = new MailThread(user.getEmail(), MessageManager.getProperty("changedConference"),
                    buildMessage(MessageManager.getProperty("changeInConference"),
                            user.getName(), oldReport.getName(), dtm.fromDateToString(oldReport.getDate()),
                            dtm.fromTimeToString(oldReport.getTime())) + "\n"
                            +
                            buildMessage(MessageManager.getProperty("location"),
                                    oldReport.getAddress().getCity(), oldReport.getAddress().getStreet(),
                                    oldReport.getAddress().getBuilding(), oldReport.getAddress().getRoom() + "\n\n")
                            +
                            buildMessage(MessageManager.getProperty("newConference"),
                                    newReport.getName(), dtm.fromDateToString(newReport.getDate()),
                                    dtm.fromTimeToString(newReport.getTime())) + "\n"
                            +
                            buildMessage(MessageManager.getProperty("location"),
                                    newReport.getAddress().getCity(), newReport.getAddress().getStreet(),
                                    newReport.getAddress().getBuilding(), newReport.getAddress().getRoom()));
            mailOperator.start();
        }
    }

    public static void notifyUserRegistration(User user, Report report) {
        MailThread mailOperator = new MailThread(user.getEmail(), MessageManager.getProperty("conferenceRegistration"),
                buildMessage(MessageManager.getProperty("successfulConferenceRegistration"),
                        user.getName(), report.getName(), dtm.fromDateToString(report.getDate()),
                        dtm.fromTimeToString(report.getTime()))
                        + "\n" +
                        buildMessage(MessageManager.getProperty("location"),
                                report.getAddress().getCity(), report.getAddress().getStreet(),
                                report.getAddress().getBuilding(), report.getAddress().getRoom()));
        mailOperator.start();
    }

    public static void assignment(User user) {
        MailThread mailOperator = new MailThread(user.getEmail(), MessageManager.getProperty("assigment"),
                buildMessage(MessageManager.getProperty("assigmentChange"),
                        user.getName(), user.getPosition()));
        mailOperator.start();
    }

    private static String buildMessage(String message, String... args) {
        String[] arr = message.split(" ");
        StringBuilder sb = new StringBuilder();
        int count = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].equals("$")) {
                arr[i] = args[count++];
            }
            sb.append(arr[i]).append(" ");
        }
        return sb.toString();
    }
}
