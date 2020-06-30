package com.pm.background.admin.sys.controller;
import com.pm.background.common.utils.R;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * 总览信息
 *
 * @author fengshuonan
 * @Date 2017年3月4日23:05:54
 */
@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    /**
     * 跳转到黑板
     */
    @RequestMapping
    public Object blackboard() {
        return R.ok("首页");
    }
}
