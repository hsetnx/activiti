package com.common.tools.common.mq;

/**
 * @Author: jingyan
 * @Time: 2017/4/26 19:36
 * @Describe:
 */
public class DefaultPullConsumer {
/*
    private final Logger logger = LoggerFactory.getLogger(DefaultPushConsumer.class);
    //消费者基本信息
    private ConsumerInfo consumerInfo;
    //订阅主题信息
    private SubscribeInfo subscribeInfo;
    //push 消费者
    private DefaultMQPullConsumer defaultMQPullConsumer;

    *//**
     * @Author: jingyan
     * @Time: 2017/4/26 16:58
     * @Describe:初始化方法
     *//*
    public void init() throws InterruptedException, MQClientException {
        logger.info("DefaultPushConsumer initialize start...");
        logger.info("消费者基本信息：" + consumerInfo.toString());
        logger.info("订阅主题信息：" + subscribeInfo.toString());

        defaultMQPullConsumer = new DefaultMQPullConsumer();
        //服务器地址
        defaultMQPullConsumer.setNamesrvAddr(consumerInfo.getNamesrvAddr());
        //消费者组
        defaultMQPullConsumer.setConsumerGroup(consumerInfo.getConsumerGroup());
        //订阅信息
        defaultMQPullConsumer.subscribe(subscribeInfo.getTopic(), subscribeInfo.getTag());
        //消费起始位置（队列前后）
        defaultMQPullConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        //最大批量消息处理数目
        defaultMQPullConsumer.setConsumeMessageBatchMaxSize(consumerInfo.getConsumeMessageBatchMaxSize());
        //设置为集群消费(区别于广播消费)
        defaultMQPullConsumer.setMessageModel(MessageModel.CLUSTERING);
        //具体消费监听代理类
        defaultMQPullConsumer.registerMessageListener(subscribeInfo.getMessageListenerConcurrently());
        //启动
        defaultMQPullConsumer.start();

        logger.info("DefaultPushConsumer initialize success...");
    }

    public void destroy() {
        defaultMQPushConsumer.shutdown();
    }

    public ConsumerInfo getConsumerInfo() {
        return consumerInfo;
    }

    public void setConsumerInfo(ConsumerInfo consumerInfo) {
        this.consumerInfo = consumerInfo;
    }

    public SubscribeInfo getSubscribeInfo() {
        return subscribeInfo;
    }

    public void setSubscribeInfo(SubscribeInfo subscribeInfo) {
        this.subscribeInfo = subscribeInfo;
    }

    public DefaultMQPushConsumer getDefaultMQPushConsumer() {
        return defaultMQPushConsumer;
    }

    public void setDefaultMQPushConsumer(DefaultMQPushConsumer defaultMQPushConsumer) {
        this.defaultMQPushConsumer = defaultMQPushConsumer;
    }*/
}
