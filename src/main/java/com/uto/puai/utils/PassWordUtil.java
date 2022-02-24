package com.uto.puai.utils;

import cn.hutool.core.util.StrUtil;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.jasypt.util.password.BasicPasswordEncryptor;
import org.jasypt.util.password.PasswordEncryptor;

/**
 * @author Richard
 * @version 1.0
 * @description jaspty加解密工具
 * @date 2021/12/14 23:36
 */
public class PassWordUtil implements PasswordEncryptor {

    public static PooledPBEStringEncryptor encryptor;
    private static BasicPasswordEncryptor basicPasswordEncryptor = new BasicPasswordEncryptor();

    static {
        encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        String passwordSecret = System.getProperty("JASYPT_ENCRYPTOR_PASSWORD");
        config.setPassword(StrUtil.isBlank(passwordSecret) ? System.getenv("JASYPT_ENCRYPTOR_PASSWORD") : passwordSecret);
        config.setAlgorithm("PBEWITHHMACSHA512ANDAES_256");
        config.setKeyObtentionIterations("1000");
        config.setPoolSize("1");
        config.setProviderName("SunJCE");
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setIvGeneratorClassName("org.jasypt.iv.RandomIvGenerator");
        config.setStringOutputType("base64");
        encryptor.setConfig(config);
    }

    @Override
    public String encryptPassword(String password) {
        return encryptor.encrypt(password);
    }

    @Override
    public boolean checkPassword(String plainPassword, String encryptedPassword) {
        return basicPasswordEncryptor.checkPassword(plainPassword, encryptedPassword);
    }
}
