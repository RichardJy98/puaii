package com.uto.puai.controller;

import com.uto.puai.common.Result;
import com.uto.puai.enums.ResultCode;
import com.uto.puai.exceptions.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Richard
 * @version 1.0
 * @description 全局异常处理类
 * @date 2021/8/4 下午 5:12
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 全局异常处理方法
     * @param request 请求
     * @param e       异常对象
     * @return 返回结果
     */
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public Result defaultErrorHandler(HttpServletRequest request, Exception e) {
        e.printStackTrace();
        log.error("请求接口[ {} ]异常,错误信息: [ {} ]", request.getRequestURL().toString(), e.getMessage());
        Result result = new Result().setSuccessful(false);
        if (!(e instanceof BaseException)) {
            //说明出现未知错误
            result = result.setCode(ResultCode.SYSTEM_INNER_ERROR.getCode()).setMsg(ResultCode.SYSTEM_INNER_ERROR.getMsg());
        } else {
            String msg = e.getMessage();
            result = result.setCode(Integer.parseInt(msg.substring(0,5))).setMsg(msg.substring(5));
        }
        return result;
    }
}
