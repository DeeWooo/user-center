package com.liyou.uc.user.exception;

import com.liyou.framework.base.AbstractBusinessRuntimeException;

/**
 * @author ivywooo
 */
public class UserCenterException extends AbstractBusinessRuntimeException {

    private int errorCode = 500;

    @Override
    public int getCode() {
        return errorCode;
    }

    public UserCenterException() {
    }

    public UserCenterException(String message) {
        super(message);
    }

    public UserCenterException(int errorCode,String message) {
        this(message);
        this.errorCode = errorCode;
    }

    public UserCenterException(Throwable cause) {
        super(cause);
    }

    public UserCenterException(int errorCode,  Throwable cause) {
        this(cause);
        this.errorCode = errorCode;
    }
}
