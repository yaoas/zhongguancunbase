package com.pm.background.admin.common.intercept;

import com.alibaba.fastjson.JSONObject;
import com.pm.background.common.utils.util.HttpUtils;
import org.apache.shiro.web.filter.authc.UserFilter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

public class ShiroUserFilter extends UserFilter {
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        if (httpRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            setHeader(httpRequest,httpResponse);
            return true;
        }
       /* if (getIpAddr(httpRequest)){
            return true;
        }*/
        return super.preHandle(request,response);
    }

    /**
     * 该方法会在验证失败后调用，这里由于是前后端分离，后台不控制页面跳转
     * 因此重写改成传输JSON数据
     */
    @Override
    protected void saveRequestAndRedirectToLogin(ServletRequest request, ServletResponse response) throws IOException {
        saveRequest(request);
        setHeader((HttpServletRequest) request,(HttpServletResponse) response);
        PrintWriter out = response.getWriter();
        HashMap<String, Object> statusMap = new HashMap<>();
        statusMap.put("code","0001");
        statusMap.put("msg","未登录");
        statusMap.put("error",true);
        out.println(JSONObject.toJSONString(statusMap));
        out.flush();
        out.close();
    }

    /**
     * 为response设置header，实现跨域
     */
    private void setHeader(HttpServletRequest request,HttpServletResponse response){
         //跨域的header设置
        response.setHeader("Access-control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Methods", request.getMethod());
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Headers", request.getHeader("Access-Control-Request-Headers"));
        //防止乱码，适用于传输JSON数据
        response.setHeader("Content-Type","application/json;charset=UTF-8"); response.setStatus(HttpStatus.OK.value());
    }

    public  boolean getIpAddr(HttpServletRequest request) {
        String ipAddress = HttpUtils.getIpAddress(request);
        if (ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")||ipAddress.equals("192.168.0.174")||ipAddress.equals("192.168.0.101")) {
            return true;
        }
        return false;
    }

}
