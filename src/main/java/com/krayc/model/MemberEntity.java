package com.krayc.model;

import javax.persistence.*;

@Entity
@Table(name = "Member", schema = "Bilet")
public class MemberEntity {
    private int mid;
    private String email;
    private String password;
    private String bankAccount;
    private int point;
    private byte isTerminated;
    private byte isEmailPassed;

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
    @Column(name = "point")
    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
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
        if (point != that.point) return false;
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
        result = 31 * result + point;
        result = 31 * result + (int) isTerminated;
        result = 31 * result + (int) isEmailPassed;
        return result;
    }
}
