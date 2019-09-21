package servises.mailManager;

import entity.Report;
import entity.Speaker;
import entity.User;
import servises.dateTimeManager.DateTimeManager;
import servises.messageManager.MessageManager;

import java.util.List;
import java.util.ResourceBundle;

/**
 * {@code MailManager} is a class for notify all participants about
 * some changes in conferences
 */

public class MailManager {
    private final ResourceBundle resourceBundle = ResourceBundle.getBundle("mail");
    private DateTimeManager dtm;
    private MessageManager message;

    public MailManager() {
        dtm = new DateTimeManager();
        message = new MessageManager();
    }

    public String getProperty(String key) {
        return resourceBundle.getString(key);
    }

    /**
     * This method notify {@link Speaker} that he was assigned to
     * some conferences.
     * @param speaker is a <code>Speaker</code> who will be notified about assigment on conference.
     * @param report is a <code>Report</code> that contains some information about conference.
     *
     */
    public void notifySpeakerAppointment(Speaker speaker, Report report) {
        message.setLocale(speaker.getLanguage());
        MailThread mailOperator = new MailThread(speaker.getEmail(), message.getProperty("conferenceAppointment"),
                buildMessage(message.getProperty("speakerAppointment"),
                        speaker.getName(), report.getName(), dtm.fromDateToString(report.getDate()),
                        dtm.fromTimeToString(report.getTime()))
                        + "\n" +
                        buildMessage(message.getProperty("location"),
                                report.getAddress().getCity(), report.getAddress().getStreet(),
                                report.getAddress().getBuilding(), report.getAddress().getRoom()));
        mailOperator.start();
    }

    /**
     * This method notify {@link Speaker} that he was dismissed from holding
     * the conference.
     * @param speaker is a <code>Speaker</code> who will be notified about dismissing from conference.
     * @param report is a <code>Report</code> that contains some information about conference.
     */
    public void notifySpeakerDismiss(Speaker speaker, Report report) {
        message.setLocale(speaker.getLanguage());
        MailThread mailOperator = new MailThread(speaker.getEmail(), message.getProperty("dismissFromConference"),
                buildMessage(message.getProperty("dismissMessage"),
                        speaker.getName(), report.getName(), dtm.fromDateToString(report.getDate()),
                        dtm.fromTimeToString(report.getTime())
                                + "\n" +
                                buildMessage(message.getProperty("location"),
                                        report.getAddress().getCity(), report.getAddress().getStreet(),
                                        report.getAddress().getBuilding(), report.getAddress().getRoom())));
        mailOperator.start();
    }

    /**
     * This method notify all participants about some changes in conference.
     * @param newReport is a <code>Report</code> that that contains new information.
     * @param oldReport is a <code>Report</code> that contains unchanged information.
     * @param userList is a List of {@link User} who will be informed about changes in conference.
     */
    public void notifyChangeConference(Report newReport, Report oldReport, List<User> userList) {
        for (User user : userList) {
            message.setLocale(user.getLanguage());
            MailThread mailOperator = new MailThread(user.getEmail(), message.getProperty("changedConference"),
                    buildMessage(message.getProperty("changeInConference"),
                            user.getName(), oldReport.getName(), dtm.fromDateToString(oldReport.getDate()),
                            dtm.fromTimeToString(oldReport.getTime())) + "\n"
                            +
                            buildMessage(message.getProperty("location"),
                                    oldReport.getAddress().getCity(), oldReport.getAddress().getStreet(),
                                    oldReport.getAddress().getBuilding(), oldReport.getAddress().getRoom() + "\n\n")
                            +
                            buildMessage(message.getProperty("newConference"),
                                    newReport.getName(), dtm.fromDateToString(newReport.getDate()),
                                    dtm.fromTimeToString(newReport.getTime())) + "\n"
                            +
                            buildMessage(message.getProperty("location"),
                                    newReport.getAddress().getCity(), newReport.getAddress().getStreet(),
                                    newReport.getAddress().getBuilding(), newReport.getAddress().getRoom()));
            mailOperator.start();
        }
    }

    /**
     * This method notify {@link User} that he was successfully registered in conference.
     * @param user is a <code>User</code> who will be informed about details of conference .
     * @param report is a <code>Report</code> that contains some information about conference.
     */
    public void notifyUserRegistration(User user, Report report) {
        message.setLocale(user.getLanguage());
        MailThread mailOperator = new MailThread(user.getEmail(), message.getProperty("conferenceRegistration"),
                buildMessage(message.getProperty("successfulConferenceRegistration"),
                        user.getName(), report.getName(), dtm.fromDateToString(report.getDate()),
                        dtm.fromTimeToString(report.getTime()))
                        + "\n" +
                        buildMessage(message.getProperty("location"),
                                report.getAddress().getCity(), report.getAddress().getStreet(),
                                report.getAddress().getBuilding(), report.getAddress().getRoom()));
        mailOperator.start();
    }

    /**
     * This method notify {@link User} that he was assigned to certain position.
     */
    public void assignment(User user) {
        message.setLocale(user.getLanguage());
        MailThread mailOperator = new MailThread(user.getEmail(), message.getProperty("assigment"),
                buildMessage(message.getProperty("assigmentChange"),
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
