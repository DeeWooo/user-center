package com.liyou.uc.integral.exception;

import com.liyou.framework.base.AbstractBusinessRuntimeException;

/**
 * @author: ivywooo
 * @date:2018/3/27
 **/

public class IntegralException  extends AbstractBusinessRuntimeException {

    private int errorCode = 500;

    @Override
    public int getCode() {
        return errorCode;
    }

    public IntegralException() {
    }

    public IntegralException(String message) {
        super(message);
    }

    public IntegralException(int errorCode,String message) {
        this(message);
        this.errorCode = errorCode;
    }

    public IntegralException(Throwable cause) {
        super(cause);
    }

    public IntegralException(int errorCode,  Throwable cause) {
        this(cause);
        this.errorCode = errorCode;
    }

}
