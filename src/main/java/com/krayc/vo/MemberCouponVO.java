package com.krayc.vo;

import java.sql.Timestamp;

public class MemberCouponVO {

    private String time;
    private String usageDescription;
    private String couponName;

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
}
