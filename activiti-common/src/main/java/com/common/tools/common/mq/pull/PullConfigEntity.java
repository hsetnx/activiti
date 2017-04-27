package com.common.tools.common.mq.pull;

import com.alibaba.rocketmq.client.consumer.PullTaskCallback;

/**
 * @Author: jingyan
 * @Time: 2017/4/27 13:05
 * @Describe:
 */
public class PullConfigEntity {

    //主题
    private String topic;
    //回调实现
    private PullTaskCallback pullTaskCallback;

    @Override
    public String toString() {
        return "PullConfigEntity{" +
                "topic='" + topic + '\'' +
                ", pullTaskCallback=" + pullTaskCallback.getClass().getSimpleName() +
                '}';
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public PullTaskCallback getPullTaskCallback() {
        return pullTaskCallback;
    }

    public void setPullTaskCallback(PullTaskCallback pullTaskCallback) {
        this.pullTaskCallback = pullTaskCallback;
    }
}
