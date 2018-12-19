package com.liyou.uc.enume;

/**
 * @author yhl
 * @Description:
 * @date：2017/12/13 0013
 * @tiem：10:41
 */
public enum Unit {

    year(1, "年"), month(2, "月"), day(3, "日");

    private Integer type;
    private String desc;

    Unit(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }


    public static Unit getName(int index) {
        for (Unit unit : Unit.values()) {
            if (unit.getType().equals(index)) {
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
