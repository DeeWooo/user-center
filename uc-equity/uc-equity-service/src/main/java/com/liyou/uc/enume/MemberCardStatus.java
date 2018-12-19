package com.liyou.uc.enume;

/**
 * @author yhl
 * @Description: 会员卡状态
 * @date：2017/12/13 0013
 * @tiem：10:41
 */
public enum MemberCardStatus {

    normal(1, "正常"), invalid(2, "失效");

    private Integer type;
    private String desc;

    MemberCardStatus(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }


    public static MemberCardStatus getName(Integer type) {
        for (MemberCardStatus unit : MemberCardStatus.values()) {
            if (unit.getType().equals(type) ) {
                return unit;
            }
        }
        return null;
    }

    public Integer getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }
}
