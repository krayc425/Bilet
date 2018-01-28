package com.krayc.vo;

public class MemberUpdateVO {

    private int mid;
    private String bankAccount;
    private String password;

    public MemberUpdateVO(int mid, String bankAccount, String password) {
        this.mid = mid;
        this.bankAccount = bankAccount;
        this.password = password;
    }

    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }


    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
