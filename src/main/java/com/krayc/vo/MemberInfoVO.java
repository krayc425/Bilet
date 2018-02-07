package com.krayc.vo;

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

    public MemberInfoVO(int mid, String email, String bankAccount, String level, int totalPoint, int currentPoint, String isTerminated, String isEmailPassed, double balance) {
        this.mid = mid;
        this.email = email;
        this.bankAccount = bankAccount;
        this.level = level;
        this.totalPoint = totalPoint;
        this.currentPoint = currentPoint;
        this.isTerminated = isTerminated;
        this.isEmailPassed = isEmailPassed;
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
