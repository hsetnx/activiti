package com.test;

import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * @Author: jingyan
 * @Time: 2017/4/26 18:33
 * @Describe:
 */
public class TestLestener implements MessageListenerConcurrently {



    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
        for(MessageExt messageExt : msgs){
            String jsonStr = new String(messageExt.getBody());
            System.out.println(jsonStr);
        }
        return null;
    }
}
