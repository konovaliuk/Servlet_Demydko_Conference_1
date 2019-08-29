package servises.mailManager;


import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

public class MailThread extends Thread {
    private MimeMessage message;
    private String sendToEmail;
    private String mailSubject;
    private String mailText;
    private Properties properties;

    public MailThread(String sendToEmail, String mailSubject, String mailText) {
        this.sendToEmail = sendToEmail;
        this.mailSubject = mailSubject;
        this.mailText = mailText;
        properties = new Properties();
        properties.put("mail.smtp.host", MailManager.getProperty("mail.smtp.host"));
        properties.put("mail.smtp.socketFactory.port", MailManager.getProperty("mail.smtp.socketFactory.port"));
        properties.put("mail.smtp.socketFactory.class", MailManager.getProperty("mail.smtp.socketFactory.class"));
        properties.put("mail.smtp.auth", MailManager.getProperty("mail.smtp.auth"));
        properties.put("mail.smtp.port", MailManager.getProperty("mail.smtp.port"));
    }

    private void init() {
        final String APIKey = MailManager.getProperty("apiKey");
        final String SecretKey = MailManager.getProperty("secretKey");
        Session mailSession = (Session.getDefaultInstance(properties,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(APIKey, SecretKey);
                    }
                }));
        mailSession.setDebug(true);

        try {
            message = new MimeMessage(mailSession);
            message.setSubject(mailSubject);
            message.setText(mailText, "UTF-8" );
            message.setFrom(new InternetAddress(APIKey,"Conference"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(sendToEmail));
        } catch (AddressException | UnsupportedEncodingException e) {
            System.err.print("Incorrect address:" + sendToEmail + " " + e);
// in log file
        } catch (MessagingException e) {
            System.err.print("Error of forming message" + e);
// in log file
        }
    }

    @Override
    public void run() {
        init();
        try {
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
            System.err.print("Send error" + e);
// in log file
        }
    }
}
