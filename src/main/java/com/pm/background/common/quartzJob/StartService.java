package com.pm.background.common.quartzJob;

import com.pm.background.admin.sys.controller.InitQuartz;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * 继承Application接口后项目启动时会按照执行顺序执行run方法
 * 通过设置Order的value来指定执行的顺序
 */
@Slf4j
//放开注释，开启定时
//@Component
public class StartService implements CommandLineRunner {
    @Autowired
    private  InitQuartz initQuartz;
    @Override
    public void run(String... args) throws Exception {
        log.info("执行StartService");
        initQuartz.task();
    }


}
