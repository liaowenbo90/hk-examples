package com.hk.example.rabbit.stream;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.core.MessageSource;
import org.springframework.messaging.support.GenericMessage;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author huangkai
 * @date 2019-01-17 14:27
 */
@SpringBootApplication
@EnableBinding(value = {PublishOutput.class, RouteKeyOutput.class, TopicOutput.class, DelayedOutput.class})
public class ProducerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProducerApplication.class, args);
    }

//    /**
//     * 发布、订阅模式 发送消息
//     *
//     * @return
//     */
//    @Bean
//    @InboundChannelAdapter(value = PublishOutput.OUTPUT, poller = @Poller(fixedRate = "4000", maxMessagesPerPoll = "1"))
//    public MessageSource<Date> send() {
//        return () -> new GenericMessage<>(new Date());
//    }

//    /**
//     * RouteKey 发送消息
//     */
//    @Bean
//    @InboundChannelAdapter(value = RouteKeyOutput.OUTPUT, poller = @Poller(fixedRate = "4000", maxMessagesPerPoll = "1"))
//    public MessageSource<Date> routeKeySend() {
//        System.out.println("sendMessage...");
//        return () -> new GenericMessage<>(new Date());
//    }

//    /**
//     * topic 发送消息
//     */
//    @Bean
//    @InboundChannelAdapter(value = TopicOutput.OUTPUT, poller = @Poller(fixedRate = "4000", maxMessagesPerPoll = "1"))
//    public MessageSource<Date> topicSend() {
//        System.out.println("topicSend sendMessage...");
//        return () -> new GenericMessage<>(new Date());
//    }

    /**
     * 延时队列
     */
    @Bean
    @InboundChannelAdapter(value = DelayedOutput.OUTPUT, poller = @Poller(fixedRate = "10000", maxMessagesPerPoll = "1"))
    public MessageSource<Date> topicSend() {
        System.out.println("delayed_output sendMessage...");
        Map<String, Object> headers = new HashMap<>();
        //延时队列需要在 header中添加 x-delay指定延时的时间，单位为毫秒
        headers.put("x-delay", 3000);
        return () -> new GenericMessage<>(new Date(), headers);
    }

}
