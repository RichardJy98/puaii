package com.uto.puai.common;

import cn.hutool.core.date.DateUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author Richard
 * @version 1.0
 * @description 统一返回信息类
 * @date 2021/8/4 下午 2:11
 */
@Data
@AllArgsConstructor
@Accessors(chain = true)
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
public class Result implements Serializable {
    private static final long serialVersionUID = 3577228372698987294L;

    /**
     * 响应码
     */
    private Integer code;

    /**
     * 是否调用成功
     */
    private Boolean successful;

    /**
     * 返回信息
     */
    private String msg;

    /**
     * 返回数据
     */
    private Object data;

    /**
     * 返回时间
     */
    private String time;


    public Result() {
        this.code = 1;
        this.successful = true;
        this.msg = "执行成功";
        this.time = DateUtil.now();
    }

    public Result(Boolean successful, Integer code, String msg) {
        this.successful = successful;
        this.code = code;
        this.msg = msg;
        this.time = DateUtil.now();
    }

    public Result(Boolean successful, Integer code, String msg, Object data) {
        this.successful = successful;
        this.code = code;
        this.msg = msg;
        this.time = DateUtil.now();
        this.data = data;
    }
}
