package com.company.newsPortal;

import com.sun.mail.util.MailSSLSocketFactory;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.security.GeneralSecurityException;
import java.util.Properties;

public class NewsSubscriber implements Subscriber {

    private String name;
    private String email_address;

    NewsSubscriber(String name, String address) {
        this.name = name;
        this.email_address = address;
    }

    //send email
    @Override
    public void notifySubbscriber(String news) {
        System.out.println("news is :" + news);
        sendEmail(news);
    }

    public String getName() {
        return name;
    }

    public void sendEmail(String msg) {
        MailSSLSocketFactory sf = null;
        try {
            sf = new MailSSLSocketFactory();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        sf.setTrustAllHosts(true);


        String recipient = email_address;

        // email ID of  Sender.
        String sender = "xxxxxxx@gmail.com";
        String password = "xxxxxxxx";

        // using host as localhost
        String host = "smtp.gmail.com";
        String port = "587";

        // Getting system properties
        Properties properties = System.getProperties();

        // Setting up mail server
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);

        properties.put("mail.imap.ssl.trust", "*");
        properties.put("mail.imap.ssl.socketFactory", sf);
        // creating session object to get properties
        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(sender, password);
            }
        });


        try {
            // MimeMessage object.
            Message message = new MimeMessage(session);

            // Set From Field: adding senders email to from field.
            message.setFrom(new InternetAddress(sender));

            // Set To Field: adding recipient's email to from field.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));

            // Set Subject: subject of the email
            message.setSubject("News Alert");

            // set body of the email.
            message.setText(msg);

            // Send email.
            Transport.send(message);
            System.out.println("Mail successfully sent");
        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}
