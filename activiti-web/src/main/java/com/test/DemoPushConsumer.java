package com.test;

import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.common.tools.common.mq.push.PushListenerAbstract;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @Author: jingyan
 * @Time: 2017/4/26 18:33
 * @Describe:
 */
public class DemoPushConsumer extends PushListenerAbstract {

    private final Logger logger = LoggerFactory.getLogger(DemoPushConsumer.class);
    @Override
    public ConsumeConcurrentlyStatus handleMsg(List<MessageExt> messageExts, ConsumeConcurrentlyContext context) {
        for(MessageExt messageExt:messageExts){
            logger.info("------ push handle ------"+messageExt.getMsgId());
        }
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }
}
