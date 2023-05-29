package com.roker.service;


import com.roker.properties.DingTalkProperties;
import com.roker.properties.EmailProperties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.Properties;

public class DingTalkService implements NotificationService {

    public DingTalkService(DingTalkProperties properties) {
        //this.properties = properties;
        //// 初始化邮箱配置
        //Properties props = new Properties();
        //props.setProperty("mail.transport.protocol", "smtp");
        //props.setProperty("mail.smtp.host", properties.getHost());
        //props.setProperty("mail.smtp.port", String.valueOf(properties.getPort()));
        //props.setProperty("mail.smtp.auth", "true");
        //props.setProperty("mail.smtp.ssl.enable", "true");
        //// 创建邮箱 Session
        //this.session = Session.getInstance(props, new Authenticator() {
        //    @Override
        //    protected PasswordAuthentication getPasswordAuthentication() {
        //        return new PasswordAuthentication(properties.getUsername(), properties.getPassword());
        //    }
        //});
    }


    @Override
    public void send(String message) {

    }

    @Override
    public <T> void sendMessage(T message) {

    }
}