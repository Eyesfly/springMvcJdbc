package com.bjrxht.task;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by Administrator on 2016/3/28.
 */
@Component
public class TaskTest {
    protected Logger logger = Logger.getLogger(getClass());
    @Scheduled(cron="0/5 * * * * ? ") //间隔5秒执行
    public void taskCycle(){
//       logger.info("我的任务启动了======================"+new Date());
    }

}
