package com.hk.springcloud.stream.kafka.transaction;

import java.time.LocalDateTime;

import com.hk.commons.JsonResult;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hk.commons.util.ByteConstants;
import com.hk.commons.util.JsonUtils;
import com.hk.core.authentication.api.SecurityContext;
import com.hk.core.authentication.api.UserPrincipal;
import com.hk.springcloud.stream.kafka.transaction.domain.City;
import com.hk.springcloud.stream.kafka.transaction.repository.jdbc.CityRepository;

import lombok.Data;

/**
 * @author huangkai
 * @date 2018-11-09 10:45
 */
@SpringBootApplication
@EnableBinding(Processor.class)
@EnableTransactionManagement
@RestController
public class TransactionApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(TransactionApplication.class, args);
    }

    @Bean
    public SecurityContext securityContext() {
        return new SecurityContext() {

            @Override
            public UserPrincipal getPrincipal() {
                return new UserPrincipal("0", "0", ByteConstants.ZERO);
            }

            @Override
            public boolean isAuthenticated() {
                return true;
            }
        };
    }

    @RequestMapping("/")
    public JsonResult<Void> index() {
        return JsonResult.success();
    }

    @Autowired
    private CityRepository cityRepository;

//    private AtomicBoolean shouldFail = new AtomicBoolean(false);

    @StreamListener(Processor.INPUT)
    @SendTo(Processor.OUTPUT)
    @Transactional
    public String process(MessageVo message) {
        LOGGER.info("------------------------------------------");
        LOGGER.info(JsonUtils.serialize(message, true));
        City city = new City();
        city.setCityType(ByteConstants.ONE);
        city.setCode(RandomStringUtils.randomNumeric(5));
        city.setParentId("0");
        city.setFullName(message.getName());
        city.setShortName(message.getName());
//        if (shouldFail.get()) {
//            shouldFail.set(false);
//            throw new RuntimeException("模拟异常...");
//        } else {
//            shouldFail.set(true);
//        }
        LOGGER.info("Saving city={}", city);
        cityRepository.save(city);
        String result = LocalDateTime.now().toString();
        LOGGER.info("Send Message...,{}", result);
        return result;
    }


    @Data
    private static class MessageVo {

        private String id;

        private String name;

        private String title;

        private LocalDateTime date;
    }
}
