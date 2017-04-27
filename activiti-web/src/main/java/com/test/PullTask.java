package com.test;

import com.alibaba.rocketmq.common.message.MessageExt;
import com.common.tools.common.mq.pull.PullCallBackAbstract;

import java.util.List;

/**
 * @Author: jingyan
 * @Time: 2017/4/27 17:52
 * @Describe:
 */
public class PullTask extends PullCallBackAbstract {


    @Override
    public boolean handleMsg(List<MessageExt> messageExts) {
        for (MessageExt messageExt : messageExts) {
            System.out.println("***************** " + new String(messageExt.getBody()));
        }
        return false;
    }
}
