//package com.hefu.rabbitmq.controller;
//
//import com.hefu.common.entity.Result;
//import com.hefu.rabbitmq.service.RabbitmqService;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//
//@Api(value = "/MQ", tags = "MQ测试接口")
//@RestController
//@RequestMapping("/MQ")
//@EnableAutoConfiguration
//public class MqTest {
//
//    @Autowired
//    RabbitmqService rabbitmqService;
//
//    @ApiOperation(value = "MQ测试")
//    @GetMapping("/get")
//    public Result Test(){
//        rabbitmqService.sendMsgByTopics();
////        rabbitmqService.sendEmalByTopics();
//        return Result.success("成功");
//    }
//}
