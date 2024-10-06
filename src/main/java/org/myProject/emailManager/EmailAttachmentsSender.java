package org.myProject.emailManager;


import jakarta.mail.*;
import jakarta.mail.internet.*;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;

public class EmailAttachmentsSender {

    public static void sendEmailWithAttachments(String host, String port, final String userName, final String password, String[] toAddress, String subject, String message, String... attachFiles) {
        try {
            Session session = createSession(host, port, userName, password);

            Message msg = createMessage(session, userName, toAddress, subject, message, attachFiles);

            Transport.send(msg);
        } catch (MessagingException | IOException e) {
            e.printStackTrace();
            // Handle or log the exception as needed
        }
    }

    private static Session createSession(String host, String port, final String userName, final String password) {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        Authenticator auth = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName, password);
            }
        };

        return Session.getInstance(properties, auth);
    }

    private static Message createMessage(Session session, String userName, String[] toAddress, String subject, String message, String... attachFiles)
            throws MessagingException, IOException {
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(userName));

        InternetAddress[] addressTo = new InternetAddress[toAddress.length];
        for (int i = 0; i < toAddress.length; i++) {
            addressTo[i] = new InternetAddress(toAddress[i]);
        }
        msg.setRecipients(Message.RecipientType.TO, addressTo);

        msg.setSubject(subject);
        msg.setSentDate(new Date());

        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent(message, "text/html");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);

        if (attachFiles != null && attachFiles.length > 0) {
            for (String filePath : attachFiles) {
                MimeBodyPart attachPart = new MimeBodyPart();
                attachPart.attachFile(filePath);
                multipart.addBodyPart(attachPart);
            }
        }

        msg.setContent(multipart);
        return msg;
    }
}
