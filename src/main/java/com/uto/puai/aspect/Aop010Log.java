package com.uto.puai.aspect;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import cn.hutool.json.JSONUtil;
import com.uto.puai.utils.RequestOrResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.context.annotation.Scope;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Objects;

/**
 * @author Richard
 * @version 1.0
 * @description 记录所有对外接口的日志
 * @date 2021/8/4 下午 9:31
 */
@Slf4j
@Scope
@Aspect
@Order(1)
@Component
public class Aop010Log {

    /**
     * 请求头中的参数名称
     */
    public static final String START_TIME = "request_start";


    /**
     * 切入点,拦截controller包下所有后缀为controller类下的前缀为generate方法
     * @author Richard
     * @date 2021/8/4 下午 9:40
     */
    @Pointcut("execution(public * com.uto.puai.controller.*Controller.*(..))")
    public void pt() {
    }

    /**
     * 前置操作记录请求的信息
     * @param point 切入点
     * @author Richard
     * @date 2021/9/7 10:27
     */
    @Before("pt()")
    public void beforeLog(JoinPoint point) {
        //获取请求对象
        HttpServletRequest request = Objects.requireNonNull(RequestOrResponseUtil.getRequest());
        //记录请求日志信息
        log.info("【请求 URL】: {}", request.getRequestURL());
        log.info("【请求  IP】: {},【数据请求方式】: {}", RequestOrResponseUtil.getIp(request), request.getMethod());
        log.info("【请求类名】: {}, 【请求方法】: {}", point.getSignature().getDeclaringTypeName(), point.getSignature().getName());

        Map<String, Object> parameterMap = RequestOrResponseUtil.getNameAndValue(point);
        log.info("【请求参数】: {}", JSONUtil.toJsonStr(parameterMap));
        //记录请求者参数
        String header = request.getHeader("User-Agent");
        UserAgent userAgent = UserAgentUtil.parse(header);
        log.info("【浏览器类型】: {},【操作系统】: {},【原始User-Agent】: {}", userAgent.getBrowser().toString(), userAgent.getOs().toString(), header);
        //打印请求相关参数
        TimeInterval timer = DateUtil.timer();
        //将计时器放入到请求中
        request.setAttribute(START_TIME, timer);
    }

    @Around("pt()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        //执行AOP被代理的方法
        Object ret = pjp.proceed();
        //记录执行的结果
        //log.info("【执行结果】: {}", JSONUtil.toJsonStr(Aop012PostData.result));
        return ret;
    }

    @AfterReturning("pt()")
    public void afterReturning() {
        TimeInterval start = (TimeInterval) Objects.requireNonNull(RequestOrResponseUtil.getRequest()).getAttribute(START_TIME);
        long costTime = start.intervalRestart();
        log.info("【请求耗时】: {}ms", costTime);
    }
}

