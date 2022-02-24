package com.uto.puai.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Richard
 * @version 1.0
 * @description 基础异常抽象类
 * @date 2021/8/4 下午 3:51
 */
@Getter
@AllArgsConstructor
public abstract class BaseException extends Exception{

    /**
     * 异常信息
     */
    private String message;
}
