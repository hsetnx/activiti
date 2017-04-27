package com.common.tools.common.mq.push;

import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;

/**
 * @Author: jingyan
 * @Time: 2017/4/26 16:22
 * @Describe:MQ订阅信息
 */
public class PushConfigEntity {
    //主题
    private String topic;
    //标签
    private String tag;
    //最大批量消息处理数目
    private int consumeMessageBatchMaxSize;
    //监听代理
    private MessageListenerConcurrently messageListenerConcurrently;

    @Override
    public String toString() {
        return "PushConfigEntity{" +
                "topic='" + topic + '\'' +
                ", tag='" + tag + '\'' +
                ", consumeMessageBatchMaxSize=" + consumeMessageBatchMaxSize +
                ", messageListenerConcurrently=" + messageListenerConcurrently.getClass().getSimpleName() +
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

    public int getConsumeMessageBatchMaxSize() {
        return consumeMessageBatchMaxSize;
    }

    public void setConsumeMessageBatchMaxSize(int consumeMessageBatchMaxSize) {
        this.consumeMessageBatchMaxSize = consumeMessageBatchMaxSize;
    }

    public MessageListenerConcurrently getMessageListenerConcurrently() {
        return messageListenerConcurrently;
    }

    public void setMessageListenerConcurrently(MessageListenerConcurrently messageListenerConcurrently) {
        this.messageListenerConcurrently = messageListenerConcurrently;
    }
}
