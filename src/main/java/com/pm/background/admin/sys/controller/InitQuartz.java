package com.pm.background.admin.sys.controller;

import com.pm.background.common.constant.Constant;
import com.pm.background.common.quartzJob.ChangeStatuJob;
import com.pm.background.common.quartzJob.QuartzJobManager;
import org.springframework.stereotype.Component;

@Component
public class InitQuartz {
    /**
     * @description:导出数据
     * @param:[Object, response]
     * @return:void
     * @author:yas
     * @remark:
     */

    public void task() throws Exception {
        //查詢门店截止日期
           QuartzJobManager instance = QuartzJobManager.getInstance();
            String cron = "0 "+"0"+" "+"0"+" * * ?";

            instance.deleteJob("appointmentEndTime",Constant.DEFAULT_JOB_GROUP_NAME);
            instance.addJob(ChangeStatuJob.class, "appointmentEndTime",Constant.DEFAULT_JOB_GROUP_NAME , cron);
    }
}
