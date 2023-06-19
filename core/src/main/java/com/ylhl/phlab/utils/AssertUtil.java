package com.ylhl.phlab.utils;

import com.ylhl.phlab.exception.ServiceException;

/**
 * 异常判定
 * @author zhengyq
 */
public class AssertUtil {
    public static void isNull(Object object, String message) {
        if (object == null) {
            throw new ServiceException(message);
        }
    }
    public static void isNull(Object object,int code, String message) {
        if (object == null) {
            throw new ServiceException(code,message);
        }
    }

    public static void notNull(Object object, String message) {
        if (object != null) {
            throw new ServiceException(message);
        }
    }
    public static void notNull(Object object,int code, String message) {
        if (object != null) {
            throw new ServiceException(code,message);
        }
    }

    public static void isTrue(boolean expression, String message) {
        if (expression) {
            throw new ServiceException(message);
        }
    }
    public static void isTrue(boolean expression,int code, String message) {
        if (expression) {
            throw new ServiceException(code,message);
        }
    }

    public static void isFalse(boolean expression, String message) {
        if (!expression) {
            throw new ServiceException(message);
        }
    }
    public static void isFalse(boolean expression,int code, String message) {
        if (!expression) {
            throw new ServiceException(code,message);
        }
    }
}
