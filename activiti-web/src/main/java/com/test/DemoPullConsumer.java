package com.test;

import com.alibaba.rocketmq.client.consumer.PullTaskContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.common.tools.common.mq.pull.PullCallBackAbstract;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @Author: jingyan
 * @Time: 2017/4/27 17:52
 * @Describe:
 */
public class DemoPullConsumer extends PullCallBackAbstract {

    private final Logger logger = LoggerFactory.getLogger(DemoPullConsumer.class);
    @Override
    public ConsumeConcurrentlyStatus handleMsg(List<MessageExt> messageExts, PullTaskContext context) {
        for(MessageExt messageExt:messageExts){
            logger.info("------ pull handle ------"+messageExt.getMsgId());
        }
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }
}
