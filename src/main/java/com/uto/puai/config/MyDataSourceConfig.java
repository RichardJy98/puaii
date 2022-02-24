package com.uto.puai.config;

import cn.hutool.setting.dialect.Props;
import com.uto.puai.utils.PassWordUtil;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.pool.HikariPool;
import lombok.extern.slf4j.Slf4j;

import java.security.AccessControlException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Richard
 * @version 1.0
 * @description 重写数据库解密数据
 * @date 2021/12/14 21:02
 */
@Slf4j
public class MyDataSourceConfig extends HikariDataSource {

    private static final char[] ID_CHARACTERS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

    /**
     * 数据库连接池
     */
    private volatile HikariPool hikariPool;


    private final HikariPool fastPathPool;

    public MyDataSourceConfig() {
        this.fastPathPool = null;
    }

    public MyDataSourceConfig(HikariConfig configuration) {
        configuration.validate();
        configuration.copyStateTo(this);
        this.hikariPool = this.fastPathPool = new HikariPool(this);
    }

    @Override
    public Connection getConnection() throws SQLException {
        if (this.isClosed()) {
            throw new SQLException("HikariDataSource " + this + " has been closed.");
        } else if (this.fastPathPool != null) {
            return this.fastPathPool.getConnection();
        } else {
            HikariPool result = this.hikariPool;
            if (result == null) {
                synchronized (this) {
                    result = this.hikariPool;
                    if (result == null) {
                        try {
                            Props prop = new Props("application.yml");
                            HikariConfig hikariConfig = new HikariConfig();
                            String password = getPassword().replace(prop.getProperty("prefix", "encryption("), "").replace(prop.getProperty("suffix", ")"), "");

                            hikariConfig.setDriverClassName(getDriverClassName());
                            hikariConfig.setUsername(getUsername());
                            hikariConfig.setJdbcUrl(getJdbcUrl());
                            hikariConfig.setMinimumIdle(prop.getInt("minimum-idle", 1));
                            hikariConfig.setAutoCommit(prop.getBool("auto-commit", true));
                            hikariConfig.setIdleTimeout(prop.getInt("idle-timeout", 10000));
                            hikariConfig.setMaxLifetime(prop.getInt("max-lifetime", 30000));
                            hikariConfig.setConnectionTimeout(prop.getInt("connection-timeout", 10000));
                            hikariConfig.setMaximumPoolSize(prop.getInt("maximum-pool-size", 1));
                            hikariConfig.setPoolName(generatePoolName());

                            String decryptedPassword = PassWordUtil.encryptor.decrypt(password);
                                hikariConfig.setPassword(decryptedPassword);
                            //数据库链接泄漏检测
                            hikariConfig.setLeakDetectionThreshold(10000L);
                            hikariPool = new HikariPool(hikariConfig);
                            this.hikariPool = result = new HikariPool(hikariConfig);
                        } catch (HikariPool.PoolInitializationException e) {
                            if (e.getCause() instanceof SQLException) {
                                throw (SQLException)e.getCause();
                            }
                            throw e;
                        }
                    }
                }
            }
            return hikariPool.getConnection();
        }
    }

    private String generatePoolName() {
        String var1 = "HikariPool-";

        try {
            synchronized (System.getProperties()) {
                String next = String.valueOf(Integer.getInteger("com.zaxxer.hikari.pool_number", 0) + 1);
                System.setProperty("com.zaxxer.hikari.pool_number", next);
                return "HikariPool-" + next;
            }
        } catch (AccessControlException var7) {
            ThreadLocalRandom random = ThreadLocalRandom.current();
            StringBuilder buf = new StringBuilder("HikariPool-");

            for (int i = 0; i < 4; ++i) {
                buf.append(ID_CHARACTERS[random.nextInt(62)]);
            }

            log.info("assigned random pool name '{}' (security manager prevented access to system properties)", buf);
            return buf.toString();
        }
    }
}
