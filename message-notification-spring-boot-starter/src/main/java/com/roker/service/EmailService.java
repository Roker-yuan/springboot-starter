package com.roker.service;

import com.roker.entity.EmailEntity;
import com.roker.properties.EmailProperties;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailService implements NotificationService {

    private final EmailProperties properties;
    private final Session session;

    public EmailService(final EmailProperties properties) {
        this.properties = properties;
        // 初始化邮箱配置
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.smtp.host", properties.getHost());
        props.setProperty("mail.smtp.port", String.valueOf(properties.getPort()));
        props.setProperty("mail.smtp.auth", "true");
        props.setProperty("mail.smtp.ssl.enable", "true");
        // 创建邮箱 Session
        this.session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(properties.getUsername(), properties.getPassword());
            }
        });
    }

    @Override
    public void send(String message) {
        try {
            // 创建邮件信息
            Message msg = new MimeMessage(session);
            msg.setSubject("properties.getSubject()");
            msg.setFrom(new InternetAddress(properties.getFrom()));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(properties.getTo()));
            msg.setText(message);
            Transport.send(msg);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> void sendMessage(T message) {
        if (!(message instanceof EmailEntity)){
            throw new RuntimeException("need type EmailEntity but Provided " + message.getClass());
        }
        EmailEntity emailEntity = (EmailEntity)message;
        for (String emailRecipient : emailEntity.getRecipient()) {
            try {
                // 创建邮件信息
                Message msg = new MimeMessage(session);
                msg.setSubject(emailEntity.getSubject());
                msg.setFrom(new InternetAddress(properties.getFrom()));
                msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailRecipient));
                msg.setText(emailEntity.getMessage());
                Transport.send(msg);
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        }

    }
}