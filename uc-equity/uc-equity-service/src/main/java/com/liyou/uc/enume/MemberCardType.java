package com.liyou.uc.enume;

/**
 * @author yhl
 * @Description: 会员卡类型
 * @date：2017/12/13 0013
 * @tiem：10:41
 */
public enum MemberCardType {

    platinum(1, "白金卡"), gold(2, "金卡"), talent(3, "达人卡");

    private Integer type;
    private String desc;

    MemberCardType(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }


    public static MemberCardType getName(Integer type) {
        for (MemberCardType unit : MemberCardType.values()) {
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
