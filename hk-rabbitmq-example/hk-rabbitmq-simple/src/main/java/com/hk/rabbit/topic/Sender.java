package com.hk.rabbit.topic;

import com.hk.rabbit.util.ConnectionUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * @author huangkai
 * @date 2019-01-17 11:10
 */
public class Sender {

    final static String EXCHANGE_NAME = "test_topic";//定义交换机的名字

    public static void main(String[] args) throws Exception {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();
        /*
         * 声明交换机
         * 参数二固定为 topic
         */
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);

        /*
         * 发布routeKey 消息
         * 如果 发送到 key.1， Recver1 和 Recver2 都能收到消息
         * 如果 发送到 key.1.xxx， 只有Recver1 能收到消息
         * 如果 发送到 abc.xxx， Recver1 和 Recver2 都能收到消息
         * 如果 发送到 abc.xxx.xxxx，只有 Recver2 都能收到消息
         */
        channel.basicPublish(EXCHANGE_NAME, "key.1.xxx", null, "发布 topic 的消息".getBytes());
        channel.close();
        ConnectionUtils.close(connection);
    }
}
