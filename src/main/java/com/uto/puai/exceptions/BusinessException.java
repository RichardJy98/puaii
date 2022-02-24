package com.uto.puai.exceptions;

/**
 * @author Richard
 * @version 1.0
 * @description 业务异常
 * @date 2021/8/4 下午 4:11
 */
public class BusinessException {

    /**
     * 字段为空
     */
    public static class FieldIsNullOrEmpty extends BaseException {

        private static final long serialVersionUID = -1110330636015995722L;

        public FieldIsNullOrEmpty(String message) {
            super(message);
        }
    }

    /**
     * 字段值有误
     */
    public static class FieldValueIsWrong extends BaseException {

        private static final long serialVersionUID = -341601510249625468L;

        public FieldValueIsWrong(String message) {
            super(message);
        }
    }


    /**
     * 应有字段不存在
     */
    public static class FieldShouldExists extends BaseException {

        private static final long serialVersionUID = -7380374171802734617L;

        public FieldShouldExists(String message) {
            super(message);
        }
    }

    /**
     * 字段已经存在
     */
    public static class FieldIsExists extends BaseException {

        private static final long serialVersionUID = -7380374171802734617L;

        public FieldIsExists(String message) {
            super(message);
        }
    }

    /**
     * Token获取失败
     */
    public static class SecurityCheckFailed extends BaseException {

        private static final long serialVersionUID = -3014608762142410542L;

        public SecurityCheckFailed(String message) {
            super(message);
        }
    }
}
