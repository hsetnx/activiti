package com.common.tools.common.mq;

import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.remoting.exception.RemotingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;

public class DefaultProducer {

    private final Logger logger = LoggerFactory.getLogger(DefaultProducer.class);

    private DefaultMQProducer defaultMQProducer;
    private String producerGroup;
    private String namesrvAddr;

    /**
     * @Author: jingyan
     * @Time: 2017/4/17 18:23
     * @Describe:defaultMQProducer init
     */
    public void init() throws MQClientException {
        // 参数信息
        logger.info("DefaultProducer initialize ...");
        logger.info("producerGroup: " + producerGroup);
        logger.info("namesrvAddr: " + namesrvAddr);
        // 初始化
        defaultMQProducer = new DefaultMQProducer(producerGroup);
        defaultMQProducer.setNamesrvAddr(namesrvAddr);
        defaultMQProducer.setInstanceName(String.valueOf(System.currentTimeMillis()));
        defaultMQProducer.start();
        logger.info("DefaultProducer start success ...");
    }

    /**
     * @Author: jingyan
     * @Time: 2017/4/17 18:24
     * @Describe:defaultMQProducer shutdown
     */
    public void destroy() {
        defaultMQProducer.shutdown();
        logger.info("DefaultMQProudcer shutdown success!");
    }

    /**
     * @Author: jingyan
     * @Time: 2017/4/17 18:24
     * @Describe:send MSG
     */
    public SendResult send(Message msg) {
        try {
            return getDefaultMQProducer().send(msg);
        } catch (MQClientException e) {
            logger.error("客户端连接错误", e);
        } catch (RemotingException e) {
            logger.error("远程错误", e);
        } catch (MQBrokerException e) {
            logger.error("消息队列异常", e);
        } catch (InterruptedException e) {
            logger.error("消息队列长连接被中断", e);
        }
        return null;
    }

    public DefaultMQProducer getDefaultMQProducer() {
        return defaultMQProducer;
    }

    public void setDefaultMQProducer(DefaultMQProducer defaultMQProducer) {
        this.defaultMQProducer = defaultMQProducer;
    }

    public String getProducerGroup() {
        return producerGroup;
    }

    public void setProducerGroup(String producerGroup) {
        this.producerGroup = producerGroup;
    }

    public String getNamesrvAddr() {
        return namesrvAddr;
    }

    public void setNamesrvAddr(String namesrvAddr) {
        this.namesrvAddr = namesrvAddr;
    }
}