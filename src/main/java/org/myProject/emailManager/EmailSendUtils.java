package org.myProject.emailManager;


import org.myProject.configManager.ConfigFactory;
import org.myProject.frameworkComstants.Constants;

import static org.myProject.configManager.ConfigFactory.getConfig;
import static org.myProject.emailManager.EmailConfig.createDefaultConfig;

public class EmailSendUtils {
    public static void sendEmail(int count_totalTCs, int count_passedTCs, int count_failedTCs, int count_skippedTCs) {
        System.out.println("WELCOME TO EMAIL ");
        if (getConfig().isSendEmail()) {
            System.out.println("****************************************");
            System.out.println("Send Email - START");
            System.out.println("****************************************");

            String messageBody = getTestCasesCountInFormat(count_totalTCs, count_passedTCs, count_failedTCs, count_skippedTCs);
            String[] attachmentFile_Report = {"Reports/ExecutionReport.html"};

            try {
                EmailAttachmentsSender.sendEmailWithAttachments(createDefaultConfig().getServer(), createDefaultConfig().getPort(), createDefaultConfig().getFrom(),
                        createDefaultConfig().getPassword(), createDefaultConfig().getTo(), createDefaultConfig().getSubject(), messageBody,
                        attachmentFile_Report);

                System.out.println("****************************************");
                System.out.println("Email sent successfully.");
                System.out.println("Send Email - END");
                System.out.println("****************************************");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static String getTestCasesCountInFormat(int count_totalTCs, int count_passedTCs, int count_failedTCs,
                                                    int count_skippedTCs) {
        System.out.println("count_totalTCs: " + count_totalTCs);
        System.out.println("count_passedTCs: " + count_passedTCs);
        System.out.println("count_failedTCs: " + count_failedTCs);
        System.out.println("count_skippedTCs: " + count_skippedTCs);

        return "<html>\r\n" + "\r\n" + " \r\n" + "\r\n"
                + "        <body> \r\n<table class=\"container\" align=\"center\" style=\"padding-top:20px\">\r\n<tr align=\"center\"><td colspan=\"4\"><h2>"
                + Constants.REPORT_TITLE + "</h2></td></tr>\r\n<tr><td>\r\n\r\n"
                + "    <table style=\"background:#67c2ef;width:120px\" >\r\n"
                + "                     <tr><td style=\"font-size: 36px\" class=\"value\" align=\"center\">"
                + count_totalTCs + "</td></tr>\r\n"
                + "                     <tr><td align=\"center\">Total</td></tr>\r\n" + "       \r\n"
                + "                </table>\r\n" + "                </td>\r\n" + "                <td>\r\n"
                + "               \r\n" + "                 <table style=\"background:#79c447;width:120px\">\r\n"
                + "                     <tr><td style=\"font-size: 36px\" class=\"value\" align=\"center\">"
                + count_passedTCs + "</td></tr>\r\n"
                + "                     <tr><td align=\"center\">Passed</td></tr>\r\n" + "       \r\n"
                + "                </table>\r\n" + "                </td>\r\n" + "                <td>\r\n"
                + "                <table style=\"background:#ff5454;width:120px\">\r\n"
                + "                     <tr><td style=\"font-size: 36px\" class=\"value\" align=\"center\">"
                + count_failedTCs + "</td></tr>\r\n"
                + "                     <tr><td align=\"center\">Failed</td></tr>\r\n" + "       \r\n"
                + "                </table>\r\n" + "                \r\n" + "                </td>\r\n"
                + "                <td>\r\n" + "                <table style=\"background:#fabb3d;width:120px\">\r\n"
                + "                     <tr><td style=\"font-size: 36px\" class=\"value\" align=\"center\">"
                + count_skippedTCs + "</td></tr>\r\n"
                + "                     <tr><td align=\"center\">Skipped</td></tr>\r\n" + "       \r\n"
                + "                </table>\r\n" + "                \r\n" + "                </td>\r\n"
                + "                </tr>\r\n"
                + "               \r\n" + "                \r\n"
                + "            </table>\r\n" + "       \r\n"
                + "<h3>Testing Configuration Details</h3>\n" +
                "\n" +
                "<table style=\"width:30%;text-align:left;border:5px double black;border-collapse:collapse;background-color:lemonchiffon;\">\n" +
                "<caption>Testing Configuration</caption>"+
                "  <tr style=\"background-color:yellowgreen;color:white;border-bottom: 1px solid #ddd\">\n" +
                "    <th>Name</th>\n" +
                "    <th>Value</th>\n" +
                "  </tr>\n" +
                "  <tr>\n" +
                "    <td><b>Environment :</b></td>\n" +
                "    <td>"
                + ConfigFactory.getConfig().environment()+"</td>\n" +
                "  </tr>\n" +
                "  <tr>\n" +
                "    <td><b>URL :</b></td>\n" +
                "    <td>"
                + ConfigFactory.getConfig().url()+"</td>\n" +
                "  </tr>\n" +
                "  <tr>\n" +
                "    <td><b>Browser :</b></td>\n" +
                "    <td>"
                +ConfigFactory.getConfig().browserName()+"</td>\n" +
                "  </tr>\n" +
                "</table>\r\n\r\n\r\n"
                + "<p><b>Note:</b> Please download reports attached below first and then go through it to know details execution!!!</p>\n" +
                " </body>\r\n" +
                "</html>";
    }

}