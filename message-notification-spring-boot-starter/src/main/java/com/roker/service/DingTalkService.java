package com.roker.service;


import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiRobotSendRequest;
import com.dingtalk.api.response.OapiRobotSendResponse;
import com.roker.entity.DingEntity;
import com.roker.entity.EmailEntity;
import com.roker.properties.DingTalkProperties;
import com.taobao.api.ApiException;
import org.apache.commons.codec.binary.Base64;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * 丁说服务
 *
 * @author lpy96
 * @date 2023/05/29
 */
public class DingTalkService implements NotificationService {
    private final DingTalkProperties properties;
    private DingTalkClient client;
    public DingTalkService(DingTalkProperties properties)  {
        this.properties = properties;
        //安全设置加签
        try {
            //安全设置加签
            Long timestamp = System.currentTimeMillis();
            String secret = properties.getSecret();
            String stringToSign = timestamp + "\n" + secret;
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(secret.getBytes("UTF-8"), "HmacSHA256"));
            byte[] signData = mac.doFinal(stringToSign.getBytes("UTF-8"));
            String sign = URLEncoder.encode(new String(Base64.encodeBase64(signData)),"UTF-8");
            //构建消息发送Client
            this.client =  new DefaultDingTalkClient(properties.getWebHook()
                    + "&timestamp=" + timestamp
                    + "&sign=" + sign);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public void send(String message) {

    }

    @Override
    public <T> void sendMessage(T message) {
        if (!(message instanceof DingEntity)){
            throw new RuntimeException("need type DingEntity but Provided " + message.getClass());
        }
        DingEntity dingEntity = (DingEntity)message;
        //创建发送请求体
        OapiRobotSendRequest request = new OapiRobotSendRequest();
        request.setMsgtype("text");
        //文本消息
        OapiRobotSendRequest.Text text = new OapiRobotSendRequest.Text();
        text.setContent(dingEntity.getContent());
        request.setText(text);
        OapiRobotSendRequest.At at = new OapiRobotSendRequest.At();
        at.setAtMobiles(Arrays.asList("xxxxx"));
        try {
            OapiRobotSendResponse response = client.execute(request);
            System.out.println(response);
        } catch (ApiException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 发送Text消息
     *
     * @param message 消息
     */
    public <T> Boolean sendTextMessage(T message) {
        if (!(message instanceof DingEntity)){
            throw new RuntimeException("need type DingEntity but Provided " + message.getClass());
        }
        DingEntity dingEntity = (DingEntity)message;
        //创建发送请求体
        OapiRobotSendRequest request = new OapiRobotSendRequest();
        request.setMsgtype("text");
        //文本消息
        OapiRobotSendRequest.Text text = new OapiRobotSendRequest.Text();
        text.setContent(dingEntity.getContent());
        request.setText(text);
        OapiRobotSendRequest.At at = new OapiRobotSendRequest.At();
        at.setAtMobiles(Arrays.asList(dingEntity.getAtMobiles()));
        try {
            OapiRobotSendResponse response = client.execute(request);
            if ("ok".equals(response.getMsg())){
                return Boolean.TRUE;
            }
        } catch (ApiException e) {
            throw new RuntimeException(e);
        }
        return Boolean.FALSE;
    }

    /**
     * 发送链接信息
     *
     * @param message 消息
     * @return {@link Boolean}
     */
    public <T> Boolean sendLinkMessage(T message) {
        if (!(message instanceof DingEntity)){
            throw new RuntimeException("need type DingEntity but Provided " + message.getClass());
        }
        DingEntity dingEntity = (DingEntity)message;
        //创建发送请求体

        OapiRobotSendRequest request = new OapiRobotSendRequest();
        request.setMsgtype("link");
        OapiRobotSendRequest.Link link = new OapiRobotSendRequest.Link();
        link.setMessageUrl(dingEntity.getMessageUrl());
        link.setPicUrl(dingEntity.getPicUrl());
        link.setTitle(dingEntity.getTitle());
        link.setText(dingEntity.getText());
        request.setLink(link);
        OapiRobotSendRequest.At at = new OapiRobotSendRequest.At();
        at.setAtMobiles(Arrays.asList(dingEntity.getAtMobiles()));
        try {
            OapiRobotSendResponse response = client.execute(request);
            if ("ok".equals(response.getMsg())){
                return Boolean.TRUE;
            }
        } catch (ApiException e) {
            throw new RuntimeException(e);
        }
        return Boolean.FALSE;
    }

    /**
     * 发送Markdown消息
     *
     * @param message 消息
     * @return {@link Boolean}
     */
    public <T> Boolean sendMarkdownMessage(T message) {
        if (!(message instanceof DingEntity)){
            throw new RuntimeException("need type DingEntity but Provided " + message.getClass());
        }
        DingEntity dingEntity = (DingEntity)message;
        //创建发送请求体
        OapiRobotSendRequest request = new OapiRobotSendRequest();
        request.setMsgtype("markdown");
        OapiRobotSendRequest.Markdown markdown = new OapiRobotSendRequest.Markdown();
        markdown.setTitle(dingEntity.getTitle());
        markdown.setText(dingEntity.getText());
        request.setMarkdown(markdown);
        OapiRobotSendRequest.At at = new OapiRobotSendRequest.At();
        at.setAtMobiles(Arrays.asList(dingEntity.getAtMobiles()));
        try {
            OapiRobotSendResponse response = client.execute(request);
            if ("ok".equals(response.getMsg())){
                return Boolean.TRUE;
            }
        } catch (ApiException e) {
            throw new RuntimeException(e);
        }
        return Boolean.FALSE;
    }

    /**
     * 发送FeedCard信息
     *
     * @param message 消息
     * @return {@link Boolean}
     */
    public <T> Boolean sendFeedCardMessage(List<T> messages) {
        List<DingEntity> linkLists = (List<DingEntity>) messages;
        //创建发送请求体
        OapiRobotSendRequest request = new OapiRobotSendRequest();
        request.setMsgtype("feedCard");
        OapiRobotSendRequest.Feedcard feedcard = new OapiRobotSendRequest.Feedcard();
        List<OapiRobotSendRequest.Links> links = new ArrayList<>();
        for (DingEntity dingEntity : linkLists) {
            OapiRobotSendRequest.Links link = new OapiRobotSendRequest.Links();
            link.setTitle(dingEntity.getTitle());
            link.setMessageURL(dingEntity.getMessageUrl());
            link.setPicURL(dingEntity.getPicUrl());
            links.add(link);
        }
        feedcard.setLinks(links);
        request.setFeedCard(feedcard);

        try {
            OapiRobotSendResponse response = client.execute(request);
            if ("ok".equals(response.getMsg())){
                return Boolean.TRUE;
            }
        } catch (ApiException e) {
            throw new RuntimeException(e);
        }
        return Boolean.FALSE;
    }
}