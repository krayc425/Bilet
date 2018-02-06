package com.krayc.vo;

import com.krayc.model.MemberCouponEntity;
import com.krayc.util.DateFormatter;

public class MemberCouponVO {

    private String time;
    private String usageDescription;
    private String couponName;
    private int mcid;

    public MemberCouponVO(MemberCouponEntity memberCouponEntity) {
        this.couponName = memberCouponEntity.getCouponByCid().getName();
        String usageDescription;
        switch (memberCouponEntity.getUsage()) {
            case 0:
                usageDescription = "未使用";
                break;
            case 1:
                usageDescription = "已使用";
                break;
            default:
                usageDescription = "";
                break;
        }
        this.mcid = memberCouponEntity.getMcid();
        this.usageDescription = usageDescription;
        this.time = DateFormatter.getDateFormatter().stringFromDate(memberCouponEntity.getTime());
    }

    public MemberCouponVO(String time, String usageDescription, String couponName) {
        this.time = time;
        this.usageDescription = usageDescription;
        this.couponName = couponName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUsageDescription() {
        return usageDescription;
    }

    public void setUsageDescription(String usageDescription) {
        this.usageDescription = usageDescription;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public int getMcid() {
        return mcid;
    }

    public void setMcid(int mcid) {
        this.mcid = mcid;
    }

}
