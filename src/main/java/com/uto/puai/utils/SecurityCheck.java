package com.uto.puai.utils;

import cn.hutool.json.JSONObject;
import org.apache.commons.codec.digest.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.TreeMap;

/**
 * @author Richard
 * @version 1.0
 * @description 接口安全校验
 * @date 2021/8/5 下午 5:51
 */
public class SecurityCheck {

    /**
     * 加密方式
     */
    public static final String SIGN_METHOD = "md5";


    /**
     * 使用md5进行校验数据安全
     * @param data          用户传递数据
     * @param request       请求头
     * @param APP_KEY       接口key
     * @param APP_SECRET    接口密钥
     * @param AUTHORIZATION 权限
     * @return 校验结果
     */
    public static Boolean md5Check(JSONObject data, HttpServletRequest request, String APP_KEY, String APP_SECRET, String AUTHORIZATION) {
        //获取对方加密后的数据
        String receiveSign = request.getHeader("Sign");

        String timeStamp = request.getHeader("Timestamp");

        //因为JSON是无序的所以需要进行排序
        if (data.containsKey("childrenvo") && data.containsKey("parentvo")) {
            return true;
        } else {
            data = new JSONObject(new TreeMap<>(data), true, true);
        }

        //将接口本身的数据数据进行拼接
        String signInfo = new StringBuilder().append(APP_SECRET).append("App-Key").append(APP_KEY).append("Authorization").append(AUTHORIZATION)
                .append("Param").append(data).append("Sign-Method").append(SIGN_METHOD)
                .append("Timestamp").append(timeStamp).append(APP_SECRET).toString();
        String sign = DigestUtils.md5Hex(signInfo.getBytes(StandardCharsets.UTF_8)).toUpperCase();

        //将两者加密过后的字符串做比较,并返回比较结果
        return sign.equals(receiveSign);
    }

    /**
     * 使用md5进行校验数据安全
     * @param data          用户传递数据
     * @param timeStamp     时间戳
     * @param APP_KEY       接口key
     * @param APP_SECRET    接口密钥
     * @param AUTHORIZATION 权限
     * @return 校验结果
     */
    public static String md5Create(JSONObject data, String timeStamp, String APP_KEY, String APP_SECRET, String AUTHORIZATION) {

        //将接口本身的数据数据进行拼接
        String signInfo = new StringBuilder().append(APP_SECRET).append("App-Key").append(APP_KEY).append("Authorization").append(AUTHORIZATION)
                .append("Param").append(data.toString()).append("Sign-Method").append(SIGN_METHOD)
                .append("Timestamp").append(timeStamp).append(APP_SECRET).toString();
        String sign = DigestUtils.md5Hex(signInfo.getBytes(StandardCharsets.UTF_8)).toUpperCase();
        return sign;
    }
}
