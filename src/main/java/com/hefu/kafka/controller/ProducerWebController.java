package com.hefu.kafka.controller;

import com.hefu.common.entity.Result;
import com.hefu.kafka.entity.KafkaMsg;
import com.hefu.mail.util.JsonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author Administrator
 */
@RestController
@Slf4j
@Api(tags = "Kafka生产者模块")
@RequestMapping("kafkaProduct")
public class ProducerWebController {

    @Resource
    private KafkaTemplate<String, String> kafkaTemplate;

    @ApiOperation(value="Kafka发送数据测试")
    @PostMapping("/sendTest")
    public Result sendMsg () {
        KafkaMsg msgLog = new KafkaMsg(1, "消息生成", 1, "消息日志", new Date()) ;
        String msg = JsonUtil.objToStr(msgLog) ;
        // 这里Topic如果不存在，会自动创建
        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send("cicada-topic", msg);

        //发送消息
        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            //推送成功
            public void onSuccess(SendResult<String, String> result) {
                log.info("cicada-topic 生产者 发送消息成功：" + result.toString());
            }
            @Override
            //推送失败
            public void onFailure(Throwable ex) {
                log.info("cicada-topic 生产者 发送消息失败：" + ex.getMessage());
            }
        });

        return Result.success(msg);
    }

}
