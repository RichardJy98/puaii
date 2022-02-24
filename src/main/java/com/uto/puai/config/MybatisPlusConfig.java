package com.uto.puai.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Richard
 * @version 1.0
 * @description 分页插件
 * @date 2021/12/30 10:25
 */
@Configuration
@ConditionalOnClass(value = {MybatisPlusInterceptor.class})
public class MybatisPlusConfig {

    /**
     返回的是拦截器的对象
     */
    @Bean
    public MybatisPlusInterceptor paginationInterceptor() {
        MybatisPlusInterceptor paginationInterceptor = new MybatisPlusInterceptor();
        paginationInterceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.SQL_SERVER2005));
        return paginationInterceptor;
    }
}