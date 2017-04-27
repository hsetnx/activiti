package com.common.tools.common.mq.pull;

import com.alibaba.rocketmq.client.consumer.MQPullConsumer;
import com.alibaba.rocketmq.client.consumer.PullResult;
import com.alibaba.rocketmq.client.consumer.PullTaskCallback;
import com.alibaba.rocketmq.client.consumer.PullTaskContext;
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
    //标签
    private String tag;
    //间隔时间
    private int pullNextDelayTimeMillis;
    //最大拉取数目
    private int maxNums;

    @Override
    public void doPullTask(MessageQueue mq, PullTaskContext context) {
        logger.info("------ PULL 模式开始消费 ------");
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
                    logger.info("------ 拉取到合法的消费数据 ------");
                    List<MessageExt> messageExts = pullResult.getMsgFoundList();
                    if (this.handleMsg(messageExts)) {
                        consumer.updateConsumeOffset(mq, pullResult.getNextBeginOffset());
                    }
                    break;
                case NO_MATCHED_MSG:
                    break;
                case NO_NEW_MSG:
                case OFFSET_ILLEGAL:
                    break;
                default:
                    break;
            }
            // 设置再过100ms后重新拉取
            context.setPullNextDelayTimeMillis(pullNextDelayTimeMillis);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @Author: jingyan
     * @Time: 2017/4/27 18:02
     * @Describe:需要实现的业务逻辑
     */
    public abstract boolean handleMsg(List<MessageExt> messageExts);




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
