package com.liyou.uc.enume;

/**
 * @author yhl
 * @Description: 会员卡使用状态
 * @date：2017/12/13 0013
 * @tiem：10:41
 */
public enum MemberCardUseStatus {

    unused(1, "未使用"), using(2, "使用中"), invalid(3, "过期");

    private Integer type;
    private String desc;

    MemberCardUseStatus(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }


    public static MemberCardUseStatus getName(Integer type) {
        for (MemberCardUseStatus unit : MemberCardUseStatus.values()) {
            if (unit.getType().equals(type)) {
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
