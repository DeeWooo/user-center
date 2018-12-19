package com.liyou.uc.exception;

import com.liyou.framework.base.AbstractBusinessRuntimeException;

/**
 * 会员卡异常
 *
 * @author yhl
 */
public class MemberCardException extends AbstractBusinessRuntimeException {

    private int errorCode = 500;

    @Override
    public int getCode() {
        return errorCode;
    }

    public MemberCardException() {
    }

    public MemberCardException(String message) {
        super(message);
    }

    public MemberCardException(int errorCode, String message) {
        this(message);
        this.errorCode = errorCode;
    }

    public MemberCardException(Throwable cause) {
        super(cause);
    }

    public MemberCardException(int errorCode, Throwable cause) {
        this(cause);
        this.errorCode = errorCode;
    }
}
