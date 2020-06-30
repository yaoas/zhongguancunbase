package com.pm.background.common.interceptors;

/**
 * @author Larry
 * @date 2020/5/18 0018 14:56
 * @description  拦截所有请求 进行访客数判断
 */

import com.pm.background.common.utils.util.HttpUtils;
import com.pm.background.common.utils.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

/**
 * 作用:拦截所有请求，设置rootPath给前端页面使用
 */
@Component
public class MyInterceptor implements HandlerInterceptor {
    @Autowired
    private StringRedisTemplate redisTemplate;
    /**
     * 在整个请求结束之后被调用，DispatcherServlet 渲染视图之后执行（进行资源清理工作）
     */
    @Override
    public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3) throws Exception {

    }

    /**
     * 请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）
     */
    @Override
    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3) throws Exception {

    }


    /**
     * 在请求处理之前进行调用（Controller方法调用之前）
     *
     * @return 返回true才会继续向下执行，返回false取消当前请求
     *
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
        String ipAddress = HttpUtils.getIpAddress(request);
        String userIps = redisTemplate.opsForValue().get("userIps");
        if (userIps==null){
            redisTemplate.opsForValue().set("userIps",ipAddress);
        }else{
            String[] split = StringUtils.split(userIps,",");
            List<String> strings = Arrays.asList(split);
            if (!strings.contains(ipAddress)){
                redisTemplate.opsForValue().set("userIps",userIps+","+ipAddress);
            }
        }
            redisTemplate.opsForValue().increment("PV",1);
            return true;
    }

}


