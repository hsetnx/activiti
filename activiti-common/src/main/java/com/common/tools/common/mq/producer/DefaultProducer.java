package com.common.tools.common.mq.producer;

import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.remoting.common.RemotingUtil;
import com.alibaba.rocketmq.remoting.exception.RemotingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: jingyan
 * @Time: 2017/4/27 17:40
 * @Describe:rocketMQ生产者
 */
public class DefaultProducer {

    private final Logger logger = LoggerFactory.getLogger(DefaultProducer.class);
    //MQ服务器地址
    private String namesrvAddr;
    //集群组名
    private String group;
    //生产者
    private DefaultMQProducer defaultMQProducer;

    /**
     * @Author: jingyan
     * @Time: 2017/4/17 18:23
     * @Describe:defaultMQProducer init
     */
    public void init() throws MQClientException {
        logger.info("--------- DefaultProducer initialize begin! ---------");
        defaultMQProducer = new DefaultMQProducer(group);
        defaultMQProducer.setNamesrvAddr(namesrvAddr);
        defaultMQProducer.setInstanceName("mqProducer" + RemotingUtil.getLocalAddress());
        defaultMQProducer.start();
        logger.info("--------- DefaultProducer initialize success! ---------");
        logger.info(defaultMQProducer.getInstanceName());
    }

    /**
     * @Author: jingyan
     * @Time: 2017/4/17 18:24
     * @Describe:defaultMQProducer shutdown
     */
    public void destroy() {
        logger.info("--------- DefaultMQProudcer shutdown begin! ---------");
        defaultMQProducer.shutdown();
        logger.info("--------- DefaultMQProudcer shutdown success! ---------");
    }

    /**
     * @Author: jingyan
     * @Time: 2017/4/17 18:24
     * @Describe:send MSG
     */
    public SendResult send(Message msg) {
        try {
            logger.info("DefaultProducer_MQ发送消息配置：" + defaultMQProducer.toString());
            SendResult sendResult = defaultMQProducer.send(msg);
            logger.info("DefaultProducer_MQ发送消息结果：" + sendResult.toString());
            return sendResult;
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
}