package com.krayc.model;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "Member", schema = "Bilet")
public class MemberEntity {

    private int mid;
    private String email;
    private String password;
    private String bankAccount;
    private int totalPoint;
    private int currentPoint;
    private byte isTerminated;
    private byte isEmailPassed;
    private Collection<MemberCouponEntity> memberCoupons;
    private Collection<OrderEntity> orders;

    @Id
    @Column(name = "mid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    @Basic
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "bankAccount")
    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    @Basic
    @Column(name = "totalPoint")
    public int getTotalPoint() {
        return totalPoint;
    }

    public void setTotalPoint(int totalPoint) {
        this.totalPoint = totalPoint;
    }

    @Basic
    @Column(name = "currentPoint")
    public int getCurrentPoint() {
        return currentPoint;
    }

    public void setCurrentPoint(int currentPoint) {
        this.currentPoint = currentPoint;
    }

    @Basic
    @Column(name = "isTerminated")
    public byte getIsTerminated() {
        return isTerminated;
    }

    public void setIsTerminated(byte isTerminated) {
        this.isTerminated = isTerminated;
    }

    @Basic
    @Column(name = "isEmailPassed")
    public byte getIsEmailPassed() {
        return isEmailPassed;
    }

    public void setIsEmailPassed(byte isEmailPassed) {
        this.isEmailPassed = isEmailPassed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MemberEntity that = (MemberEntity) o;

        if (mid != that.mid) return false;
        if (totalPoint != that.totalPoint) return false;
        if (currentPoint != that.currentPoint) return false;
        if (isTerminated != that.isTerminated) return false;
        if (isEmailPassed != that.isEmailPassed) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        if (bankAccount != null ? !bankAccount.equals(that.bankAccount) : that.bankAccount != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = mid;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (bankAccount != null ? bankAccount.hashCode() : 0);
        result = 31 * result + totalPoint;
        result = 31 * result + currentPoint;
        result = 31 * result + (int) isTerminated;
        result = 31 * result + (int) isEmailPassed;
        return result;
    }

    @OneToMany(mappedBy = "memberByMid", fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    public Collection<MemberCouponEntity> getMemberCoupons() {
        return memberCoupons;
    }

    public void setMemberCoupons(Collection<MemberCouponEntity> memberCoupons) {
        this.memberCoupons = memberCoupons;
    }

    @OneToMany(mappedBy = "memberByMid", fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    public Collection<OrderEntity> getOrders() {
        return orders;
    }

    public void setOrders(Collection<OrderEntity> orders) {
        this.orders = orders;
    }

}
