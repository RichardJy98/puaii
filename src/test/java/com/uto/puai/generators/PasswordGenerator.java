package com.uto.puai.generators;

import org.jasypt.encryption.StringEncryptor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;

/**
 * @author Richard
 * @version 1.0
 * @description 密码生成器
 * @date 2021/11/21 20:24
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PasswordGenerator {

    @Autowired
    private Environment env;

    @Resource
    private StringEncryptor codeSheepEncryptorBean;

    @Test
    public void test() {
        System.err.println(env.getProperty("JASYPTPASSWORD"));

        // 首先获取配置文件里的原始明文信息
        String username = env.getProperty("spring.datasource.username");
        String password = env.getProperty("spring.datasource.password");

        // 加密
        String userNameEncryptedPswd = codeSheepEncryptorBean.encrypt(username);
        String passWordEncryptedPswd = codeSheepEncryptorBean.encrypt(password);

        // 打印加密前后的结果对比
        System.out.println("用户名原始明文密码为：" + username);
        System.out.println("密码原始明文密码为：" + password);
        System.out.println("====================================");
        System.out.println("用户名原始明文密码加密后的结果为：" + userNameEncryptedPswd);
        System.out.println("密码明文密码加密后的结果为：" + passWordEncryptedPswd);
        System.out.println("====================================");
        System.out.println("用户名原始明文密码解密后的结果为：" + codeSheepEncryptorBean.decrypt(userNameEncryptedPswd));
        System.out.println("密码明文密码解密后的结果为：" + codeSheepEncryptorBean.decrypt(passWordEncryptedPswd));
    }

    @Test
    public void decrypt() {
        ArrayList<String> passwordList = new ArrayList<>();
        passwordList.add("Qx0ybicNgatH5vSN9Cu2Cw0kz9qrU+09w9xzyu2LP8k1bN1PqB+cbTZO20XxKv2Q");
        passwordList.add("3hPUX41Is1iJoA2AL/6cGvfvnCuNEkI3TyWhl67wmDqFgLGHp24KL+PNUA5izASE");

        for (String password : passwordList) {
            System.out.println("解密后的明文为: " + codeSheepEncryptorBean.decrypt(password));
        }
    }
}
