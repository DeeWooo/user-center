package com.liyou.uc.others.dto;

import java.io.Serializable;

public class CustomerInfoParam  implements Serializable {
	private String imei;
	private String idfa;
	private String token;   //为了兼容原个推功能，传入token和cid
	private String cid;
	private String machinetype;  //机型
	private int osType;  //0:android 1:ios

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
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getMachinetype() {
		return machinetype;
	}
	public void setMachinetype(String machinetype) {
		this.machinetype = machinetype;
	}
	public int getOsType() {
		return osType;
	}
	public void setOsType(int osType) {
		this.osType = osType;
	}



}
