package com.krayc.model;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

@Entity
@Table(name = "Order", schema = "Bilet")
public class OrderEntity {
    private int oid;
    private Timestamp orderTime;
    private byte status;
    private EventEntity eventByEid;
    private MemberEntity memberByMid;
    private CouponEntity couponByCid;
    private Collection<OrderEventSeatEntity> orderEventSeats;

    @Id
    @Column(name = "oid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getOid() {
        return oid;
    }

    public void setOid(int oid) {
        this.oid = oid;
    }

    @Basic
    @Column(name = "orderTime")
    public Timestamp getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String timeString) {
        this.orderTime = Timestamp.valueOf(timeString);
    }

    public void setOrderTime(Timestamp orderTime) {
        this.orderTime = orderTime;
    }

    @Basic
    @Column(name = "status")
    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderEntity that = (OrderEntity) o;

        if (oid != that.oid) return false;
        if (status != that.status) return false;
        if (orderTime != null ? !orderTime.equals(that.orderTime) : that.orderTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = oid;
        result = 31 * result + (orderTime != null ? orderTime.hashCode() : 0);
        result = 31 * result + (int) status;
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "eid", referencedColumnName = "eid", nullable = false)
    public EventEntity getEventByEid() {
        return eventByEid;
    }

    public void setEventByEid(EventEntity eventByEid) {
        this.eventByEid = eventByEid;
    }

    @ManyToOne
    @JoinColumn(name = "mid", referencedColumnName = "mid", nullable = false)
    public MemberEntity getMemberByMid() {
        return memberByMid;
    }

    public void setMemberByMid(MemberEntity memberByMid) {
        this.memberByMid = memberByMid;
    }

    @OneToOne
    @JoinColumn(name = "cid", referencedColumnName = "cid")
    public CouponEntity getCouponByCid() {
        return couponByCid;
    }

    public void setCouponByCid(CouponEntity couponByCid) {
        this.couponByCid = couponByCid;
    }

    @OneToMany(mappedBy = "orderByOid", fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    public Collection<OrderEventSeatEntity> getOrderEventSeats() {
        return orderEventSeats;
    }

    public void setOrderEventSeats(Collection<OrderEventSeatEntity> orderEventSeats) {
        this.orderEventSeats = orderEventSeats;
    }
}
