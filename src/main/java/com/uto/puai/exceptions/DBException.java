package com.uto.puai.exceptions;

/**
 * @author Richard
 * @version 1.0
 * @description 数据库异常
 * @date 2021/8/4 下午 3:54
 */
public class DBException {

    /**
     * SQL执行错误
     */
    public static class BadExecution extends BaseException {
        private static final long serialVersionUID = 4964769933886954156L;

        public BadExecution(String message) {
            super(message);
        }
    }

    /**
     * 找不到数据
     */
    public static class NoData extends BaseException {
        private static final long serialVersionUID = 8777415230393628334L;

        public NoData(String message) {
            super(message);
        }
    }

    /**
     * 返回多行数据
     */
    public static class MoreData extends BaseException {
        private static final long serialVersionUID = -3987707665150073980L;

        public MoreData(String message) {
            super(message);
        }
    }

    /**
     * 无效参数错误
     */
    public static class InvalidParam extends BaseException {
        private static final long serialVersionUID = 4235225697094262603L;

        public InvalidParam(String message) {
            super(message);
        }
    }

}
