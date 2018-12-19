package com.liyou.uc.user.client.auth.qq;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * @author ivywu
 */
public class QqAuthUserInfoData implements Serializable {

    /**
     * 返回码。0: 正确返回。其它: 失败。
     */
    private Integer ret;

    /**
     * 如果ret<0，会有相应的错误信息提示，返回数据全部用UTF-8编码。
     */
    private String msg;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 大小为30×30像素的QQ空间头像URL。
     */
    private String figureurl;

    /**
     * 大小为50×50像素的QQ空间头像URL。
     */
    @JsonProperty("figureurl_1")
    private String figureurl1;

    /**
     * 大小为100×100像素的QQ空间头像URL。
     */
    @JsonProperty("figureurl_2")
    private String figureurl2;

    /**
     * 大小为40×40像素的QQ头像URL。
     */
    @JsonProperty("figureurl_qq_1")
    private String figureurlQq1;

    /**
     * 大小为100×100像素的QQ头像URL。若没有100×100像素的QQ头像，则返回大小为40×40像素的QQ头像URL。
     */
    @JsonProperty("figureurl_qq_2")
    private String figureurlQq2;

    private String openId;

    public Integer getRet() {
        return ret;
    }

    public void setRet(Integer ret) {
        this.ret = ret;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getFigureurl() {
        return figureurl;
    }

    public void setFigureurl(String figureurl) {
        this.figureurl = figureurl;
    }

    public String getFigureurl1() {
        return figureurl1;
    }

    public void setFigureurl1(String figureurl1) {
        this.figureurl1 = figureurl1;
    }

    public String getFigureurl2() {
        return figureurl2;
    }

    public void setFigureurl2(String figureurl2) {
        this.figureurl2 = figureurl2;
    }

    public String getFigureurlQq1() {
        return figureurlQq1;
    }

    public void setFigureurlQq1(String figureurlQq1) {
        this.figureurlQq1 = figureurlQq1;
    }

    public String getFigureurlQq2() {
        return figureurlQq2;
    }

    public void setFigureurlQq2(String figureurlQq2) {
        this.figureurlQq2 = figureurlQq2;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }
}
