package com.uto.puai.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.uto.puai.mapper.LogRecordMapper;
import com.uto.puai.service.LogRecordService;
import org.springframework.stereotype.Service;

import java.util.logging.LogRecord;

/**
 * @author Richard
 * @version 1.0
 * @description: 日志接口实现类
 * @date 2021/12/30 14:42
 */
@Service
public class LogRecordServiceImpl extends ServiceImpl<LogRecordMapper, LogRecord> implements LogRecordService {

}
