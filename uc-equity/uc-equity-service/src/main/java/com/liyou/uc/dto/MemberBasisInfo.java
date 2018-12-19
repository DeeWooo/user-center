package com.liyou.uc.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author YHL
 * @version V1.0
 * @Description: 会员卡信息
 * @date 2018-04-25
 */
public class MemberBasisInfo implements Serializable {

    private Long id;

    /**
     * 1：金卡 、2：超级白金卡、3：银卡 、4：达人账户
     */
    private Integer type;

    /**
     * 可查询范围 1：城市
     */
    private Integer purview;

    /**
     * 金卡/超级白金卡/银卡/达人账户
     */
    private String name;

    /**
     * 原始价格
     */
    private Long originalPrice;

    /**
     * 实际销售价格
     */
    private Long sellPrice;

    /**
     * 折扣
     */
    private Double discount;

    /**
     * 过期时间
     */
    private Date expiredDate;

    /**
     * 1:日 2：月 3：年
     */
    private Integer unit;

    /**
     * 限制 时间
     */
    private Integer limitUnit;

    /**
     * 1:启用 2：失效
     */
    private Integer status;

    /**
     * 总数量
     */
    private Integer totalNum;

    /**
     * 大图路径
     */
    private String image;

    /**
     * 小图路径
     */
    private String icon;

    private Date createTime;

    private Date updateTime;

    private Integer sort;

    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getPurview() {
        return purview;
    }

    public void setPurview(Integer purview) {
        this.purview = purview;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(Long originalPrice) {
        this.originalPrice = originalPrice;
    }

    public Long getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(Long sellPrice) {
        this.sellPrice = sellPrice;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Date getExpiredDate() {
        return expiredDate==null?null:(Date) expiredDate.clone();
    }

    public void setExpiredDate(Date expiredDate) {
        this.expiredDate = expiredDate==null?null:(Date) expiredDate.clone();
    }

    public Integer getUnit() {
        return unit;
    }

    public void setUnit(Integer unit) {
        this.unit = unit;
    }

    public Integer getLimitUnit() {
        return limitUnit;
    }

    public void setLimitUnit(Integer limitUnit) {
        this.limitUnit = limitUnit;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Date getCreateTime() {
        return createTime==null?null:(Date) createTime.clone();
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime==null?null:(Date) createTime.clone();
    }

    public Date getUpdateTime() {
        return updateTime==null?null:(Date) updateTime.clone();
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime =updateTime==null?null: (Date) updateTime.clone();
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("MemberBasisInfo{");
        sb.append("id=").append(id);
        sb.append(", type=").append(type);
        sb.append(", purview=").append(purview);
        sb.append(", name='").append(name).append('\'');
        sb.append(", originalPrice=").append(originalPrice);
        sb.append(", sellPrice=").append(sellPrice);
        sb.append(", discount=").append(discount);
        sb.append(", expiredDate=").append(expiredDate);
        sb.append(", unit=").append(unit);
        sb.append(", limitUnit=").append(limitUnit);
        sb.append(", status=").append(status);
        sb.append(", totalNum=").append(totalNum);
        sb.append(", image='").append(image).append('\'');
        sb.append(", icon='").append(icon).append('\'');
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", sort=").append(sort);
        sb.append(", description='").append(description).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
