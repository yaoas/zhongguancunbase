package com.pm.background.common.quartzJob;

import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Component;


/***
 * 自定义定时任务
 */
@Component
public class ChangeStatuJob implements BaseTaskJob {
    @Override
    public void execute(JobExecutionContext context) {
        //写自己的业务逻辑
    }
}
