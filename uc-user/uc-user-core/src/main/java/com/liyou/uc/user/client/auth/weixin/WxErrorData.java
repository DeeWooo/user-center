package com.liyou.uc.user.client.auth.weixin;

import java.io.Serializable;

/**
 * @author: ivywooo
 * @date:2018/5/5
 **/

public class WxErrorData implements Serializable {

    private  Integer errcode;

    private String errmsg;

    public Integer getErrcode() {
        return errcode;
    }

    public void setErrcode(Integer errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }
}
