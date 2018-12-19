package com.liyou.uc.user.dto;

import com.liyou.uc.user.enums.BrokerCertifyStatus;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: ivywooo
 * @date:2018/4/9
 **/

public class BrokerExtro extends Broker implements Serializable {

    private Date interestExpirationDate;

    public Date getInterestExpirationDate() {
        return interestExpirationDate==null?null:(Date) interestExpirationDate.clone();
    }

    public void setInterestExpirationDate(Date interestExpirationDate) {
        this.interestExpirationDate =  interestExpirationDate==null?null:(Date) interestExpirationDate.clone();
    }
}
