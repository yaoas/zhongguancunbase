package com.pm.background.common.aop;
import com.pm.background.common.exception.BeamException;
import com.pm.background.common.utils.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 全局的的异常拦截器（拦截所有的控制器）（带有@RequestMapping注解的方法上都会拦截）
 *
 * @author fengshuonan
 * @date 2016年11月12日 下午3:19:56
 */
public class BaseControllerExceptionHandler {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * 拦截业务异常
     */
    @ExceptionHandler(BeamException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Object notFount(BeamException e) {
        log.error("业务异常:", e);
        return R.fail( e.getMessage());
    }

    /**
     * 拦截未知的运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Object notFount(RuntimeException e) {
        log.error("运行时异常:", e);
        return R.fail( e.getMessage());
    }

}
