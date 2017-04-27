package com.common.tools.common.mq.push;

import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @Author: jingyan
 * @Time: 2017/4/27 20:40
 * @Describe:
 */
public abstract class PushListenerAbstract implements MessageListenerConcurrently {

    private final Logger logger = LoggerFactory.getLogger(PushListenerAbstract.class);
    //订阅主题
    private String topic;
    //订阅标签
    private String tag;
    //最大批量消息处理数目
    private int maxNums;

    /**
     * @Author: jingyan
     * @Time: 2017/4/27 20:44
     * @Describe: 业务实现逻辑
     */
    public abstract ConsumeConcurrentlyStatus handleMsg(List<MessageExt> messageExts, ConsumeConcurrentlyContext context);

    /**
     * @Author: jingyan
     * @Time: 2017/4/27 20:45
     * @Describe:推送入口
     */
    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
        logger.info("------ push consume message ------");
        return this.handleMsg(msgs, context);
    }

    @Override
    public String toString() {
        return "PushListenerAbstract{" +
                "topic='" + topic + '\'' +
                ", tag='" + tag + '\'' +
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

    public int getMaxNums() {
        return maxNums;
    }

    public void setMaxNums(int maxNums) {
        this.maxNums = maxNums;
    }
}
