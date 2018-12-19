package com.liyou.uc.enume;

/**
 * @author yhl
 * @Description: 会员卡状态
 * @date：2017/12/13 0013
 * @tiem：10:41
 */
public enum MemberCardBuySource {

    consumer(1, "用户版APP"), managerSys(2, "后台管理系统");

    private Integer type;
    private String desc;

    MemberCardBuySource(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }


    public static MemberCardBuySource getName(Integer type) {
        for (MemberCardBuySource unit : MemberCardBuySource.values()) {
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
