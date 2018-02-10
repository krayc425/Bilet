package com.krayc.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "MemberCoupon", schema = "Bilet")
public class MemberCouponEntity {

    private int mcid;
    private Timestamp time;
    private int usage;
    private MemberEntity memberByMid;
    private CouponEntity couponByCid;

    @Id
    @Column(name = "mcid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getMcid() {
        return mcid;
    }

    public void setMcid(int mcid) {
        this.mcid = mcid;
    }

    @Basic
    @Column(name = "time")
    public Timestamp getTime() {
        return time;
    }

    public void setTime(String timeString) {
        this.time = Timestamp.valueOf(timeString);
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    @Basic
    @Column(name = "usageNum")
    public int getUsage() {
        return usage;
    }

    public void setUsage(int usage) {
        this.usage = usage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MemberCouponEntity that = (MemberCouponEntity) o;

        if (mcid != that.mcid) return false;
        if (usage != that.usage) return false;
        if (time != null ? !time.equals(that.time) : that.time != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = mcid;
        result = 31 * result + (time != null ? time.hashCode() : 0);
        result = 31 * result + usage;
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "mid", referencedColumnName = "mid", nullable = false)
    public MemberEntity getMemberByMid() {
        return memberByMid;
    }

    public void setMemberByMid(MemberEntity memberByMid) {
        this.memberByMid = memberByMid;
    }

    @ManyToOne
    @JoinColumn(name = "cid", referencedColumnName = "cid", nullable = false)
    public CouponEntity getCouponByCid() {
        return couponByCid;
    }

    public void setCouponByCid(CouponEntity couponByCid) {
        this.couponByCid = couponByCid;
    }

}
