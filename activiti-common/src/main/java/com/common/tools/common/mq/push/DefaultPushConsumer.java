package com.common.tools.common.mq.push;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.alibaba.rocketmq.common.protocol.heartbeat.MessageModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: jingyan
 * @Time: 2017/4/26 16:37
 * @Describe:
 */
public class DefaultPushConsumer {

    private final Logger logger = LoggerFactory.getLogger(DefaultPushConsumer.class);
    //MQ服务器地址
    private String namesrvAddr;
    //集群组名
    private String group;
    //订阅主题信息
    private PushConfigEntity pushConfigEntity;
    //push 消费者
    private DefaultMQPushConsumer defaultMQPushConsumer;

    /**
     * @Author: jingyan
     * @Time: 2017/4/26 16:58
     * @Describe:初始化方法
     */
    public void init() throws InterruptedException, MQClientException {
        logger.info("--------- DefaultPushConsumer initialize begin! ---------");
        logger.info("--------- 订阅主题信息：" + pushConfigEntity.toString() + " ---------");

        defaultMQPushConsumer = new DefaultMQPushConsumer();
        //服务器地址
        defaultMQPushConsumer.setNamesrvAddr(namesrvAddr);
        //消费者组
        defaultMQPushConsumer.setConsumerGroup(group);
        //订阅信息
        defaultMQPushConsumer.subscribe(pushConfigEntity.getTopic(), pushConfigEntity.getTag());
        //消费起始位置（队列前后）
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        //最大批量消息处理数目
        defaultMQPushConsumer.setConsumeMessageBatchMaxSize(pushConfigEntity.getConsumeMessageBatchMaxSize());
        //设置为集群消费(区别于广播消费)
        defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
        //具体消费监听代理类
        defaultMQPushConsumer.registerMessageListener(pushConfigEntity.getMessageListenerConcurrently());
        //启动
        defaultMQPushConsumer.start();
        logger.info("--------- DefaultPushConsumer initialize success! ---------");
    }

    public void destroy() {
        logger.info("--------- DefaultPushConsumer shutdown start! ---------");
        defaultMQPushConsumer.shutdown();
        logger.info("--------- DefaultPushConsumer shutdown success! ---------");
    }

    public String getNamesrvAddr() {
        return namesrvAddr;
    }

    public void setNamesrvAddr(String namesrvAddr) {
        this.namesrvAddr = namesrvAddr;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public PushConfigEntity getPushConfigEntity() {
        return pushConfigEntity;
    }

    public void setPushConfigEntity(PushConfigEntity pushConfigEntity) {
        this.pushConfigEntity = pushConfigEntity;
    }

    public DefaultMQPushConsumer getDefaultMQPushConsumer() {
        return defaultMQPushConsumer;
    }

    public void setDefaultMQPushConsumer(DefaultMQPushConsumer defaultMQPushConsumer) {
        this.defaultMQPushConsumer = defaultMQPushConsumer;
    }
}
