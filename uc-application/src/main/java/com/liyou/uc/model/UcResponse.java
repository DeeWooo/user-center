package com.liyou.uc.model;


import com.liyou.framework.base.AbstractBusinessException;
import com.liyou.framework.base.AbstractBusinessRuntimeException;
import com.liyou.framework.base.model.Response;
import com.liyou.uc.user.exception.UserCenterException;

import java.io.Serializable;
import java.sql.SQLException;

/**
 * @author: ivywooo
 * @date:2018/4/25
 **/

public final class UcResponse<T> implements Serializable {

    /**
     * 默认异常
     **/
    private static final int DEFAULT_ERROR_CODE = -1;
    /**
     * 参数异常
     **/
    private static final int PARAMETER_ERROR_CODE = -2;
    /**
     * 提交重复
     **/
    private static final int SUBMITTED_REPEATED_CODE = -3;

    private int resultCode = 0;
    private T body;
    private String errMsg;


    public UcResponse() {
    }

    public UcResponse(T body) {
        this.resultCode = 0;
        this.errMsg = null;
        this.body = body;
    }

    public UcResponse(int resultCode, String errMsg) {
        this.resultCode = resultCode;
        this.errMsg = errMsg;
    }

    public UcResponse(int resultCode, T body, String errMsg) {
        this.resultCode = resultCode;
        this.body = body;
        this.errMsg = errMsg;
    }

    public UcResponse(int resultCode, T body) {
        this.resultCode = resultCode;
        this.body = body;
    }


    public static <T> UcResponse<T> success(T data) {
        UcResponse<T> response = new UcResponse(data);
        response.errMsg = null;
        response.resultCode = 0;
        response.body = data;
        return response;
    }

    public static <T> UcResponse<T> failure( String errMsg) {
        UcResponse<T> response = new UcResponse(DEFAULT_ERROR_CODE,errMsg);
        return response;
    }

    public static <T> UcResponse<T> failure(int resultCode, String errMsg) {
        UcResponse<T> response = new UcResponse(resultCode,errMsg);
        return response;
    }


    public static <T> UcResponse<T> failure(Exception exception) {
        if (null == exception) {
            return new UcResponse(DEFAULT_ERROR_CODE, null);
        }

        Throwable throwable = rootCause(exception);
        String message = throwable.getMessage();

        if (exception instanceof UserCenterException) {
            return failure(((UserCenterException) exception).getCode(),
                    exception.getMessage());
        }
        if (exception instanceof IllegalArgumentException) {
            return failure(PARAMETER_ERROR_CODE, message);
        }
        if (exception instanceof AbstractBusinessException) {
            return failure(((AbstractBusinessException) exception).getCode(), message);
        }
        if (throwable instanceof AbstractBusinessException) {
            return failure(((AbstractBusinessException) throwable).getCode(), message);
        }
        if (exception instanceof AbstractBusinessRuntimeException) {
            return failure(((AbstractBusinessRuntimeException) exception).getCode(), message);
        }
        if (throwable instanceof AbstractBusinessRuntimeException) {
            return failure(((AbstractBusinessRuntimeException) throwable).getCode(), message);
        }
        if ("javax.validation.ValidationException".equals(throwable.getClass().getName())) {
            return parameterError(message);
        }
        if (throwable instanceof SQLException && "23000".endsWith(((SQLException) throwable).getSQLState())) {
            return submittedRepeated(message);
        }
        return failure(message);
    }

    public static <T> UcResponse<T> parameterError(String message){
        UcResponse<T> response = failure(PARAMETER_ERROR_CODE,message);
        return response;
    }

    private static Throwable rootCause(Throwable throwable) {
        Throwable cause;
        while ((cause = throwable.getCause()) != null) {
            throwable = cause;
        }
        return throwable;
    }

    private static <T> UcResponse<T> submittedRepeated(String message){
        UcResponse<T> response = failure(SUBMITTED_REPEATED_CODE,message);
        return response;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}
