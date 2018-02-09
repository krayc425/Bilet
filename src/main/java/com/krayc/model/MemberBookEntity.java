package com.krayc.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "MemberBook", schema = "Bilet")
public class MemberBookEntity {

    private int mbid;
    private Double amount;
    private byte type;
    private Timestamp time;
    private MemberEntity member;
    private OrderEntity order;

    @Id
    @Column(name = "mbid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getMbid() {
        return mbid;
    }

    public void setMbid(int mbid) {
        this.mbid = mbid;
    }

    @Basic
    @Column(name = "amount")
    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @Basic
    @Column(name = "type")
    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    @Basic
    @Column(name = "time")
    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MemberBookEntity that = (MemberBookEntity) o;

        if (mbid != that.mbid) return false;
        if (type != that.type) return false;
        if (amount != null ? !amount.equals(that.amount) : that.amount != null) return false;
        if (time != null ? !time.equals(that.time) : that.time != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = mbid;
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        result = 31 * result + (int) type;
        result = 31 * result + (time != null ? time.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "mid", referencedColumnName = "mid", nullable = false)
    public MemberEntity getMember() {
        return member;
    }

    public void setMember(MemberEntity member) {
        this.member = member;
    }

    @ManyToOne
    @JoinColumn(name = "oid", referencedColumnName = "oid")
    public OrderEntity getOrder() {
        return order;
    }

    public void setOrder(OrderEntity order) {
        this.order = order;
    }

}
