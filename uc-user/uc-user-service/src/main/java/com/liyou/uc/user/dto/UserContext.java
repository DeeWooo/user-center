package com.liyou.uc.user.dto;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: ivywooo
 * @date:2018/3/5
 **/

public class UserContext implements Serializable {

    private User user = new User();
    private Authorization authorization = new Authorization();
    private List<Role> roleList = new ArrayList<>();
    private Broker broker = new Broker();
    private List<UserInterests> userInterestsList = new ArrayList<>();
    private Certifier certifier = new Certifier();

    public UserContext(User user, Authorization authorization, List<Role> roles) {
        this.user = user;
        this.authorization = authorization;
        this.roleList = roles;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Authorization getAuthorization() {
        return authorization;
    }

    public void setAuthorization(Authorization authorization) {
        this.authorization = authorization;
    }

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

    public Broker getBroker() {
        return broker;
    }

    public void setBroker(Broker broker) {
        this.broker = broker;
    }

    public List<UserInterests> getUserInterestsList() {
        return userInterestsList;
    }

    public void setUserInterestsList(List<UserInterests> userInterestsList) {
        this.userInterestsList = userInterestsList;
    }

    public Certifier getCertifier() {
        return certifier;
    }

    public void setCertifier(Certifier certifier) {
        this.certifier = certifier;
    }
}