package com.krayc.vo;

import com.krayc.model.MemberEntity;

public class MemberInfoVO {

    private int mid;
    private String email;
    private String bankAccount;
    private String level;
    private int totalPoint;
    private int currentPoint;
    private String isTerminated;
    private String isEmailPassed;
    private String balance;

    public MemberInfoVO(MemberEntity memberEntity1, String level, Double balance) {
        this.mid = memberEntity1.getMid();
        this.email = memberEntity1.getEmail();
        this.bankAccount = memberEntity1.getBankAccount();
        this.level = level;
        this.totalPoint = memberEntity1.getTotalPoint();
        this.currentPoint = memberEntity1.getCurrentPoint();
        this.isTerminated = memberEntity1.getIsTerminated() == Byte.valueOf("1") ? "已被注销" : "可以使用";
        this.isEmailPassed = memberEntity1.getIsEmailPassed() == Byte.valueOf("1") ? "已经激活" : "尚未激活";
        this.balance = String.format("%.2f", balance);
    }

    public int getTotalPoint() {
        return totalPoint;
    }

    public void setTotalPoint(int totalPoint) {
        this.totalPoint = totalPoint;
    }

    public int getCurrentPoint() {
        return currentPoint;
    }

    public void setCurrentPoint(int currentPoint) {
        this.currentPoint = currentPoint;
    }

    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getIsTerminated() {
        return isTerminated;
    }

    public void setIsTerminated(String isTerminated) {
        this.isTerminated = isTerminated;
    }

    public String getIsEmailPassed() {
        return isEmailPassed;
    }

    public void setIsEmailPassed(String isEmailPassed) {
        this.isEmailPassed = isEmailPassed;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

}
