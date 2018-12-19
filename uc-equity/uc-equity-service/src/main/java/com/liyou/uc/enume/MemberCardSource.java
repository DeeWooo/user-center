package com.liyou.uc.enume;

/**
 * @author yhl
 * @Description: 会员卡来源
 * @date：2017/12/13 0013
 * @tiem：10:41
 */
public enum MemberCardSource {

    buy(1, "正常购买"), handsel(2, "活动赠送");

    private Integer type;
    private String desc;

    MemberCardSource(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }


    public static MemberCardSource getName(Integer type) {
        for (MemberCardSource unit : MemberCardSource.values()) {
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
