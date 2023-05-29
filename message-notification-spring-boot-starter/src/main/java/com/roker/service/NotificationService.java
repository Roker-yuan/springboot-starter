package com.roker.service;

/**
 * @作者: Roker
 * @时间: 2023/5/28 22:09
 * @Copyright: Don`t be the same,be better!
 * @Description: $描述$
 */
public interface NotificationService {
    public void send(String message);

    public <T> void sendMessage(T message);
}
