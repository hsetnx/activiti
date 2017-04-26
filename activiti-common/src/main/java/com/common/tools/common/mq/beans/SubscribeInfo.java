package com.common.tools.common.mq.beans;

import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;

/**
 * @Author: jingyan
 * @Time: 2017/4/26 16:22
 * @Describe:MQ订阅信息
 */
public class SubscribeInfo {
    //主题
    private String topic;
    //标签
    private String tag;
    //监听代理
    private MessageListenerConcurrently messageListenerConcurrently;

    @Override
    public String toString() {
        return "SubscribeInfo{" +
                "topic='" + topic + '\'' +
                ", tag='" + tag + '\'' +
                '}';
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public MessageListenerConcurrently getMessageListenerConcurrently() {
        return messageListenerConcurrently;
    }

    public void setMessageListenerConcurrently(MessageListenerConcurrently messageListenerConcurrently) {
        this.messageListenerConcurrently = messageListenerConcurrently;
    }
}
