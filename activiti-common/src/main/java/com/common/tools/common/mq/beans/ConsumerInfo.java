package com.common.tools.common.mq.beans;

/**
 * @Author: jingyan
 * @Time: 2017/4/26 16:45
 * @Describe:消费者基本信息
 */
public class ConsumerInfo {

    //MQ服务器地址
    private String namesrvAddr;
    //消费组
    private String consumerGroup;
    //最大批量消息处理数目
    private int consumeMessageBatchMaxSize;

    @Override
    public String toString() {
        return "ConsumerInfo{" +
                "namesrvAddr='" + namesrvAddr + '\'' +
                ", consumerGroup='" + consumerGroup + '\'' +
                ", consumeMessageBatchMaxSize=" + consumeMessageBatchMaxSize +
                '}';
    }

    public String getNamesrvAddr() {
        return namesrvAddr;
    }

    public void setNamesrvAddr(String namesrvAddr) {
        this.namesrvAddr = namesrvAddr;
    }

    public String getConsumerGroup() {
        return consumerGroup;
    }

    public void setConsumerGroup(String consumerGroup) {
        this.consumerGroup = consumerGroup;
    }

    public int getConsumeMessageBatchMaxSize() {
        return consumeMessageBatchMaxSize;
    }

    public void setConsumeMessageBatchMaxSize(int consumeMessageBatchMaxSize) {
        this.consumeMessageBatchMaxSize = consumeMessageBatchMaxSize;
    }
}
