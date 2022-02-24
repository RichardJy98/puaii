package com.uto.puai.exceptions;

/**
 * @author Richard
 * @version 1.0
 * @description TODO
 * @date 2021/8/4 下午 5:24
 */
public class SystemException {

    /**
     * 404异常
     */
    public static class PathNotFounded extends BaseException{

        private static final long serialVersionUID = 6334601117750448544L;

        public PathNotFounded(String message) {
            super(message);
        }
    }

    /**
     * 500异常
     */
    public static class ProgramException extends BaseException {

        private static final long serialVersionUID = 1653724249984898495L;

        public ProgramException(String message) {
            super(message);
        }
    }
}
