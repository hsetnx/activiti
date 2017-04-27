package com.common.tools.common.mq.pull;

import com.alibaba.rocketmq.client.consumer.MQPullConsumerScheduleService;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.protocol.heartbeat.MessageModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: jingyan
 * @Time: 2017/4/26 19:36
 * @Describe:
 */
public class DefaultPullConsumer {

    private final Logger logger = LoggerFactory.getLogger(DefaultPullConsumer.class);
    //MQ服务器地址
    private String namesrvAddr;
    //集群组名
    private String group;
    //回调对象
    private PullCallBackAbstract pullCallBackAbstract;
    //pull消费者
    private MQPullConsumerScheduleService mQPullConsumerScheduleService;

    /**
     * @Author: jingyan
     * @Time: 2017/4/27 18:40
     * @Describe:初始化
     */
    public void init() throws InterruptedException, MQClientException {
        logger.info("--------- DefaultPullConsumer initialize begin! ---------");
        logger.info("--------- 订阅主题信息：" + pullCallBackAbstract.toString() + " ---------");
        mQPullConsumerScheduleService = new MQPullConsumerScheduleService(group);
        mQPullConsumerScheduleService.getDefaultMQPullConsumer().setNamesrvAddr(namesrvAddr);
        mQPullConsumerScheduleService.setMessageModel(MessageModel.CLUSTERING);
        mQPullConsumerScheduleService.registerPullTaskCallback(pullCallBackAbstract.getTopic(), pullCallBackAbstract);
        mQPullConsumerScheduleService.start();
        logger.info("--------- DefaultPullConsumer initialize success! ---------");
    }

    /**
     * @Author: jingyan
     * @Time: 2017/4/27 18:40
     * @Describe:停止服务
     */
    public void destroy() {
        logger.info("--------- DefaultPullConsumer shutdown start! ---------");
        mQPullConsumerScheduleService.shutdown();
        logger.info("--------- DefaultPullConsumer shutdown success! ---------");
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

    public PullCallBackAbstract getPullCallBackAbstract() {
        return pullCallBackAbstract;
    }

    public void setPullCallBackAbstract(PullCallBackAbstract pullCallBackAbstract) {
        this.pullCallBackAbstract = pullCallBackAbstract;
    }
}
