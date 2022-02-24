package com.uto.puai.controller;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.uto.puai.service.LogRecordService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.logging.LogRecord;

/**
 * @author Richard
 * @version 1.0
 * @description 日志查询类
 * @date 2021/12/30 11:27
 */
@RestController
@RequestMapping("log")
public class LogController {

    @Resource
    private LogRecordService logService;

    @GetMapping("changePage/{current}/{size}")
    public String pageQuery(@PathVariable("current") int current, @PathVariable("size") int size) {
        Page<LogRecord> page = logService.page(new Page<LogRecord>(current, size));
        return new JSONObject(page).toString();
    }

    @GetMapping("initPage")
    public String pageQuery() {
        Page<LogRecord> page = logService.page(new Page<LogRecord>(1, 100));
        return new JSONObject(page).toString();
    }

    @PostMapping("queryTime")
    public String queryTime(@RequestBody JSONObject times) {
        return new JSONObject(logService.page(new Page<LogRecord>(times.getInt("current"), times.getInt("size")),
                new QueryWrapper<LogRecord>().between("time", times.getStr("begin"), times.getStr("end")))).toString();
    }
}
