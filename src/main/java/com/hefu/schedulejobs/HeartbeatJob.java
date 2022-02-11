package com.hefu.schedulejobs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 定时任务
 *
 * 第一位，表示秒，取值0-59
 * 第二位，表示分，取值0-59
 * 第三位，表示小时，取值0-23
 * 第四位，日期天/日，取值1-31
 * 第五位，日期月份，取值1-12
 * 第六位，星期，取值1-7，1表示星期天，2表示星期一
 * 第七位，年份，可以留空，取值1970-2099
 * @Author administrator
 */
@Component
@Slf4j
public class HeartbeatJob {

    /**
     * 检查状态1
     */
//    @Scheduled(cron = "0 30 12 * * ?")
//    public void checkState1() {
//        log.info(">>>>> cron中午12:30上传检查开始....");
//        log.info(">>>>> cron中午12:30上传检查完成....");
//    }

    /**
     * 检查状态2
     */
//    @Scheduled(cron = "0 0 18 * * ?")
//    public void checkState2() {
//        log.info(">>>>> cron晚上18:00上传检查开始....");
//        log.info(">>>>> cron晚上18:00上传检查完成....");
//    }


}
