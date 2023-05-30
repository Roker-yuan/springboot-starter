package com.roker.service;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.google.gson.Gson;
import com.roker.properties.SMSProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SMSService implements NotificationService {
    static final Logger log = LoggerFactory.getLogger(SMSService.class);

    private IAcsClient client;

    public SMSService(SMSProperties properties) {
        DefaultProfile profile = DefaultProfile.getProfile(properties.getRegionId(), properties.getAccessKeyId(), properties.getAccessKeySecret());
        IAcsClient client = new DefaultAcsClient(profile);
        this.client = client;
    }


    @Override
    public void send(String message) {
        SendSmsRequest request = new SendSmsRequest();
        request.setSignName("阿里云短信测试");
        request.setTemplateCode("SMS_154950909");
        request.setPhoneNumbers("17346555506");
        request.setTemplateParam("{\"code\":\"1234\"}");

        try {
            SendSmsResponse response = client.getAcsResponse(request);
            log.info(new Gson().toJson(response));
        }catch (ClientException e) {
            log.error("ErrCode:{},ErrMsg:{},RequestId:{}",e.getErrCode(),e.getErrMsg(),e.getRequestId());
        }
    }

    @Override
    public <T> void sendMessage(T message) {
        SendSmsRequest request = new SendSmsRequest();
        request.setSignName("阿里云短信测试");
        request.setTemplateCode("SMS_154950909");
        request.setPhoneNumbers("17346555506");
        request.setTemplateParam("{\"code\":\"1234\"}");

        try {
            SendSmsResponse response = client.getAcsResponse(request);
            log.info(new Gson().toJson(response));
        }catch (ClientException e) {
            log.error("ErrCode:{},ErrMsg:{},RequestId:{}",e.getErrCode(),e.getErrMsg(),e.getRequestId());
        }
    }
}

