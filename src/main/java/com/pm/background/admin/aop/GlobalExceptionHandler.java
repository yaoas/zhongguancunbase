package com.pm.background.admin.aop;
import com.alibaba.fastjson.JSON;
import com.pm.background.common.aop.BaseControllerExceptionHandler;
import com.pm.background.common.utils.R;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;

/**
 * 全局的的异常拦截器（拦截所有的控制器）（带有@RequestMapping注解的方法上都会拦截）
 *
 * @author hs
 * @date 2018年9月19日 下午19:19:56
 */
@ControllerAdvice
public class GlobalExceptionHandler extends BaseControllerExceptionHandler {

    private Logger log = LoggerFactory.getLogger(this.getClass());


    //不合法的参数异常
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public R exception(IllegalArgumentException e) {
        e.printStackTrace();
        log.error("参数不合法",e);
        return R.fail(500,e.getMessage());
    }

    //响应信息已发出 无法再次操作 500
    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public R exception(IllegalStateException e) {
        e.printStackTrace();
        log.error("响应信息已发出 无法再次操作",e);
        return R.fail(500,e.getMessage());
    }
    //端口被占用 500
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public R exception(BindException e) {
        e.printStackTrace();
        log.error("端口信息异常",e);
        return R.fail(500,e.getAllErrors().get(0).getDefaultMessage());
    }
    /**
     * @author Larry
     * @date 2020/5/19 0019 11:50
     * @param
     * @return
     * @description 抓取所有参数异常
     **/
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public R exception(MethodArgumentNotValidException e) {
        log.error("参数异常",e);
        BindingResult bindingResult = e.getBindingResult();
        return R.fail(400,bindingResult.getAllErrors().get(0).getDefaultMessage());
    }
    /**
     * @author Larry
     * @date 2020/5/19 0019 9:13
     * @param [e]
     * @return com.pm.background.common.utils.R
     * @description 抓取404异常
     **/
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public R exception(NoHandlerFoundException e) {
        log.error("页面找不到",e);
        return R.fail(404,e.getRequestURL()+"路径不存在");
    }
    //权限不足  403
    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public R exception(UnauthorizedException e) {
        log.error("权限不足",e);
        return R.fail(e.getMessage());
    }

    //sql语句错误  403
    @ExceptionHandler(value = {SQLSyntaxErrorException.class, BadSqlGrammarException.class,SQLException.class})
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public R exception(SQLException e) {
        log.error("执行数据库语句错误",e);
        return R.fail("执行数据库语句错误");
    }
    /**
     * @author Larry
     * @date 2020/5/19 0019 11:50
     * @param
     * @return
     * @description 处理其他未到达controller的常见异常
     **/

    @ExceptionHandler(value = {NumberFormatException.class,HttpMessageNotReadableException.class, MissingServletRequestParameterException.class, MaxUploadSizeExceededException.class, HttpRequestMethodNotSupportedException.class})
    public void springFrameworkException(Exception e, HttpServletRequest request, ServletResponse response) throws Exception {
            String message="未知异常";
            if (e instanceof MissingServletRequestParameterException) {
                message="请求参数缺失";
            } else if (e instanceof HttpMessageNotReadableException) {
                message="请求参数读取失败,请检查参数是否格式化正确";
            } else if (e instanceof MaxUploadSizeExceededException) {
                message="上传文件大小超过限制";
            } else if (e instanceof HttpRequestMethodNotSupportedException) {
                message="请求方式错误";
            } else if (e instanceof NumberFormatException) {
                message="传入实体类的属性对应类型错误";
            }
            log.error(message,e);
        // 使用ServletOutputStream的write方法返回异常处理信息
        response.setContentType("application/json;charset=UTF-8");
        ServletOutputStream servletOutputStream = response.getOutputStream();
        String s = JSON.toJSONString(R.fail(400, message));
        servletOutputStream.write(s.getBytes());
    }

    //通用异常处理  500
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public R exception(Exception e) {
        log.error("未知异常",e);
        return R.fail(e.getMessage());
    }

}
