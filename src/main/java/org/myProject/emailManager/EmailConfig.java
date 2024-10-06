package org.myProject.emailManager;


import org.myProject.configManager.ConfigFactory;
import org.myProject.frameworkComstants.Constants;

public class EmailConfig {
        private final String server;
        private final String port;
        private final String from;
        private final String password;
        private final String[] to;
        private final String subject;

    public EmailConfig(String server, String port, String from, String password, String[] to, String subject) {
        this.server = server;
        this.port = port;
        this.from = from;
        this.password = password;
        this.to = to;
        this.subject = subject;
    }

    public String getServer() {
        return server;
    }

    public String getPort() {
        return port;
    }

    public String getFrom() {
        return from;
    }

    public String getPassword() {
        return password;
    }

    public String[] getTo() {
        return to;
    }

    public String getSubject() {
        return subject;
    }

    public static EmailConfig createDefaultConfig() {
        String server = "smtp.gmail.com";
        String port = "587";
        String from = "adubeyinbo@gmail.com";
        String password = "abc@12345";
        String[] to = ConfigFactory.getConfig().emailTo();
        String subject = Constants.REPORT_SUBJECT;

        return new EmailConfig(server, port, from, password, to, subject);
    }
}