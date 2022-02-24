package com.uto.puai.config;

import com.uto.puai.utils.PassWordUtil;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author Richard
 * @version 1.0
 * @description 加密配置类
 * @date 2021/11/21 16:43
 */
@Configuration
public class CodeSheepEncryptorCfg {

    @Bean(name = "codeSheepEncryptorBean")
    public StringEncryptor codesheepStringEncryptor() {
        return PassWordUtil.encryptor;
    }
}
