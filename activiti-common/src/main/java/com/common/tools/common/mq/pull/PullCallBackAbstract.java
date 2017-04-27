package com.common.tools.common.mq.pull;

import com.alibaba.rocketmq.client.consumer.MQPullConsumer;
import com.alibaba.rocketmq.client.consumer.PullResult;
import com.alibaba.rocketmq.client.consumer.PullTaskCallback;
import com.alibaba.rocketmq.client.consumer.PullTaskContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.alibaba.rocketmq.common.message.MessageQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @Author: jingyan
 * @Time: 2017/4/27 18:00
 * @Describe:PULL回调抽象类
 */
public abstract class PullCallBackAbstract implements PullTaskCallback {

    private final Logger logger = LoggerFactory.getLogger(PullCallBackAbstract.class);
    //主题
    private String topic;
    //标签
    private String tag;
    //间隔时间
    private int pullNextDelayTimeMillis;
    //最大拉取数目
    private int maxNums;

    /**
     * @Author: jingyan
     * @Time: 2017/4/27 18:02
     * @Describe:需要实现的业务逻辑
     */
    public abstract ConsumeConcurrentlyStatus handleMsg(List<MessageExt> messageExts, PullTaskContext context);

    /**
     * @Author: jingyan
     * @Time: 2017/4/27 20:44
     * @Describe:拉取入口
     */
    @Override
    public void doPullTask(MessageQueue mq, PullTaskContext context) {
        logger.info("------ pull doPullTask ------" + mq.getQueueId());
        MQPullConsumer consumer = context.getPullConsumer();
        try {
            // 获取从哪里拉取
            long offset = consumer.fetchConsumeOffset(mq, false);
            if (offset < 0) {
                offset = 0;
            }
            //队列 tag 起始位置+数目
            PullResult pullResult = consumer.pull(mq, tag, offset, maxNums);
            //校验拉倒的消息结果
            switch (pullResult.getPullStatus()) {
                case FOUND:
                    logger.info("------ pull found msg , start consumption ...------");
                    List<MessageExt> messageExts = pullResult.getMsgFoundList();
                    switch (this.handleMsg(messageExts, context)) { //消费消息,判断结果
                        case CONSUME_SUCCESS:
                            logger.info("------ CONSUME_SUCCESS ------");
                            consumer.updateConsumeOffset(mq, pullResult.getNextBeginOffset());//成功消费，修改偏移量
                            break;
                        case RECONSUME_LATER:
                            logger.info("------ RECONSUME_LATER ------");
                            break;
                        default:
                            break;
                    }
                    break;
                case NO_MATCHED_MSG:
                    break;
                case NO_NEW_MSG:
                    break;
                case OFFSET_ILLEGAL:
                    break;
                default:
                    break;
            }
            // 设置拉取间隔时间
            context.setPullNextDelayTimeMillis(pullNextDelayTimeMillis);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "PullCallBackAbstract{" +
                "topic='" + topic + '\'' +
                ", tag='" + tag + '\'' +
                ", pullNextDelayTimeMillis=" + pullNextDelayTimeMillis +
                ", maxNums=" + maxNums +
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

    public int getPullNextDelayTimeMillis() {
        return pullNextDelayTimeMillis;
    }

    public void setPullNextDelayTimeMillis(int pullNextDelayTimeMillis) {
        this.pullNextDelayTimeMillis = pullNextDelayTimeMillis;
    }

    public int getMaxNums() {
        return maxNums;
    }

    public void setMaxNums(int maxNums) {
        this.maxNums = maxNums;
    }

}
