package com.hefu.kafka.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @author Administrator
 */
//@Component
@Slf4j
public class ConsumerMsg {

    //@KafkaListener(topics = "cicada-topic", groupId = "${spring.kafka.consumer.group-id}")
    public void listenMsg (ConsumerRecord<?,String> record) {
        String value = record.value();
        log.info("ConsumerMsg====>>" + value);
    }
}