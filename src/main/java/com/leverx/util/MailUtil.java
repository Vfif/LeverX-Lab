package com.leverx.util;

import com.leverx.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Component
public class MailUtil {

    @Value("${mail.text}")
    private String text;

    public static final String SUBJECT = "Code for activation";
    public static final String SERVER_MAIL = "gurskayamariya69@gmail.com";
    public static final String SERVER_PASSWORD = "vfif6969";
    public static final boolean AUTHENTICATION = true;
    public static final boolean STARTTLS = true;
    public static final String HOST = "smtp.gmail.com";
    public static final int PORT = 587;

    public void sendCode(User user, String code) throws MessagingException {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", AUTHENTICATION);
        properties.put("mail.smtp.starttls.enable", STARTTLS);
        properties.put("mail.smtp.host", HOST);
        properties.put("mail.smtp.port", PORT);

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SERVER_MAIL, SERVER_PASSWORD);
            }
        });


        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(SERVER_MAIL));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(user.getEmail()));
        message.setSubject(SUBJECT);

        text = text.replace("%NAME%", user.getFirstName());
        text = text.replace("%SURNAME%", user.getLastName());
        text = text.replace("%CODE%", code);

        message.setText(text);
        Transport.send(message);
    }
}
