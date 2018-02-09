package com.krayc.model;

import com.krayc.util.OrderStatus;
import com.krayc.util.OrderType;
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
    private OrderStatus status;
    private EventEntity eventByEid;
    private MemberEntity memberByMid;
    private MemberCouponEntity memberCouponEntity;
    private Collection<OrderEventSeatEntity> orderEventSeats;
    private OrderType type;
    private Collection<MemberBookEntity> memberBooks;

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
    @Enumerated(EnumType.ORDINAL)
    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
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
        result = 31 * result + status.hashCode();
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
    @JoinColumn(name = "mcid", referencedColumnName = "mcid")
    public MemberCouponEntity getMemberCouponEntity() {
        return memberCouponEntity;
    }

    public void setMemberCouponEntity(MemberCouponEntity memberCouponEntity) {
        this.memberCouponEntity = memberCouponEntity;
    }

    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    public Collection<OrderEventSeatEntity> getOrderEventSeats() {
        return orderEventSeats;
    }

    public void setOrderEventSeats(Collection<OrderEventSeatEntity> orderEventSeats) {
        this.orderEventSeats = orderEventSeats;
    }

    @Basic
    @Column(name = "type")
    public OrderType getType() {
        return type;
    }

    public void setType(OrderType type) {
        this.type = type;
    }

    @OneToMany(mappedBy = "order")
    public Collection<MemberBookEntity> getMemberBooks() {
        return memberBooks;
    }

    public void setMemberBooks(Collection<MemberBookEntity> memberBooks) {
        this.memberBooks = memberBooks;
    }

}
