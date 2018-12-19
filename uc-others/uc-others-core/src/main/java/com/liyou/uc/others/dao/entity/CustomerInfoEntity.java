package com.liyou.uc.others.dao.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author: ivywooo
 * @date:2018/5/30
 **/

@Entity
@Table(name = "customer_info")
public class CustomerInfoEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;

    private String imeimd5;
    private String imei;
    private String idfa;
    private String token;
    private String mobileno;
    private String emailaddr;
    private String weixinid;

    private Date lastlogin;
    private String cid;
    private String flag;
    private String machinetype;

    private Long userId;
    private Date cdate;


    @Column(name = "userid")
    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getImeimd5() {
        return imeimd5;
    }

    public void setImeimd5(String imeimd5) {
        this.imeimd5 = imeimd5;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getIdfa() {
        return idfa;
    }

    public void setIdfa(String idfa) {
        this.idfa = idfa;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public String getEmailaddr() {
        return emailaddr;
    }

    public void setEmailaddr(String emailaddr) {
        this.emailaddr = emailaddr;
    }

    public String getWeixinid() {
        return weixinid;
    }

    public void setWeixinid(String weixinid) {
        this.weixinid = weixinid;
    }

    public Date getLastlogin() {
        return lastlogin==null?null:(Date) lastlogin.clone();
    }

    public void setLastlogin(Date lastlogin) {
        this.lastlogin = lastlogin==null?null:(Date) lastlogin.clone();
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getMachinetype() {
        return machinetype;
    }

    public void setMachinetype(String machinetype) {
        this.machinetype = machinetype;
    }

    @Column(name = "user_id")
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getCdate() {
        return cdate==null?null:(Date) cdate.clone();
    }

    public void setCdate(Date cdate) {
        this.cdate = cdate==null?null:(Date) cdate.clone();
    }

}
