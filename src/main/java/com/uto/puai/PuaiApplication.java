package com.uto.puai;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.PreDestroy;
import java.lang.management.ManagementFactory;

/**
 * @author Richard
 * @version 1.0
 * @description TODO
 * @date 2022/2/23 19:46
 */
@Slf4j
@SpringBootApplication
@EnableTransactionManagement
@MapperScan("com.uto.puai.mapper")
public class PuaiApplication implements ApplicationRunner {

    @Autowired
    public Environment env;


    public static void main(String[] args) {
        SpringApplication.run(PuaiApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        //记录开启时间
        log.warn("【项目启动时间】: {}, 【项目PID】: {}", DateUtil.now(), ManagementFactory.getRuntimeMXBean().getName().split("@")[0]);
        ////记录项目启动的环境
        log.warn("【项目环境】: {}", env.getProperty("spring.profiles.active"));
    }

    @PreDestroy
    public void destory() {
        //记录结束时间
        log.warn("【项目关闭时间】: {}, 【项目PID】: {}", DateUtil.now(), ManagementFactory.getRuntimeMXBean().getName().split("@")[0]);
    }
}
