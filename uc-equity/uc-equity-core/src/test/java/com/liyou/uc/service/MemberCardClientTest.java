package com.liyou.uc.service;


import com.liyou.framework.base.model.Response;
import com.liyou.uc.BaseTestCase;
import com.liyou.uc.dto.MemberBasisInfo;
import com.liyou.uc.enume.MemberCardStatus;
import com.liyou.uc.enume.MemberCardType;
import com.liyou.uc.enume.Unit;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class MemberCardClientTest extends BaseTestCase {


    @Autowired
    private MemberCardClient memberCardClient;

    @Test
    public void getByMemberStatus() {

        Response<List<MemberBasisInfo>> byMemberStatus = memberCardClient.getByMemberStatus(MemberCardStatus.normal, null, null);

        byMemberStatus.getData().forEach(System.out::println);

    }


    @Test
    public void getByMemberStatusAndType() {
        Response<List<MemberBasisInfo>> byMemberStatusAndType = memberCardClient.getByMemberStatusAndType(MemberCardStatus.normal, MemberCardType.gold, null, null);

        byMemberStatusAndType.getData().stream().forEach(System.out::println);

    }


    @Test
    public void getByMemberStatusAndTypeAndLimit() {

        Response<List<MemberBasisInfo>> byMemberStatusAndTypeAndLimit = memberCardClient.getByMemberStatusAndTypeAndLimit(MemberCardStatus.normal, MemberCardType.gold, Unit.year.getType(), 1, null, null);

        byMemberStatusAndTypeAndLimit.getData().forEach(System.out::println);


    }


    public void getByMemberId() {


    }


    public void getByUserMemberCardStatus() {

    }


    public void getByUserIdAndNotHandsel() {

       // memberCardClient.getByUserIdAndNotHandsel()

    }


    public void getByUserMemberCardStatusAndCardType() {
    }


    public void getByUserMemberId() {
    }


    public void addUserMemberBuyRecording() {
    }


    public void getUserMemberCard() {
    }


    public void getUserMemberCardByOrderNo() {
    }


    public void upUserMemberCardStatus() {
    }


    public void upUserMemberCardStatusAndCityIdAndHouseId() {
    }


    public void upByFirstUseDate() {
    }
}
