package com.BookStore.utils;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import java.util.Properties;
/**
 * 发送邮件的工具类
 */
public class MailUtils {

    public static void sendMail(String email, String emailMsg)
            throws AddressException, MessagingException {

        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "SMTP");
        props.setProperty("mail.host", "smtp.qq.com");
        props.setProperty("mail.smtp.auth", "true");

        Authenticator auth = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("810921727@qq.com", "ghrcarjwkdmfbbaj");
            }
        };

        Session session = Session.getInstance(props, auth);

        Message message = new MimeMessage(session);

        message.setFrom(new InternetAddress("810921727@qq.com"));

        message.setRecipient(RecipientType.TO, new InternetAddress(email));

        message.setSubject("用户激活");
        // message.setText("这是一封激活邮件，请<a href='#'>点击</a>");

        message.setContent(emailMsg, "text/html;charset=utf-8");


        Transport.send(message);
    }
}
