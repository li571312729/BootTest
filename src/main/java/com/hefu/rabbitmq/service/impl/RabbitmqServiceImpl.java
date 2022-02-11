package com.hefu.rabbitmq.service.impl;

import com.hefu.rabbitmq.config.RabbitmqConfig;
import com.hefu.rabbitmq.service.RabbitmqService;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


@Service
public class RabbitmqServiceImpl implements RabbitmqService {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Override
    public void sendMsgByTopics(){
        /**
         * 参数：
         * 1、交换机名称
         * 2、routingKey
         * 3、消息内容
         */

            String message = "恭喜您，注册成功！";
            rabbitTemplate.convertAndSend(RabbitmqConfig.EXCHANGE_NAME,"topic.sms",message);
            System.out.println(" [x] Sent '" + message + "'");


        }

//    public void sendEmalByTopics(){
//        /**
//         * 参数：
//         * 1、交换机名称
//         * 2、routingKey
//         * 3、消息内容
//         */
//            String message = "恭喜您，注册成功2！";
//            rabbitTemplate.convertAndSend(RabbitmqConfig.EXCHANGE_NAME,"topic.#.sms.#",message);
//            System.out.println(" [x] Sent '" + message + "'");
//        }

}
