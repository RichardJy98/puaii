package com.uto.puai.filters;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;
import com.uto.puai.aspect.Aop010Log;
import com.uto.puai.controller.GlobalExceptionHandler;
import com.uto.puai.utils.RequestOrResponseUtil;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Richard
 * @version 1.0
 * @description 过滤器
 * @date 2021/12/1 20:59
 */
@Component
public class LogFilter extends Filter<ILoggingEvent> {
    public static List<String> loggerNames;

    static {
        loggerNames = new ArrayList<>(3);
        loggerNames.add(Aop010Log.class.getName());
        loggerNames.add(GlobalExceptionHandler.class.getName());
        loggerNames.add(RequestOrResponseUtil.class.getName());
    }

    @Override
    public FilterReply decide(ILoggingEvent event) {
        //只将自定义的日志记录到数据库
        if (loggerNames.contains(event.getLoggerName())) {
            return FilterReply.ACCEPT;
        }
        return FilterReply.DENY;
    }
}
