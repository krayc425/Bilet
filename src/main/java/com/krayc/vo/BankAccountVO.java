package com.krayc.vo;

import com.krayc.model.BankAccountEntity;

public class BankAccountVO {

    private String bankAccount;
    private String balance;

    public BankAccountVO(BankAccountEntity bankAccountEntity) {
        this.bankAccount = bankAccountEntity.getBankAccount();
        this.balance = String.format("%.2f", bankAccountEntity.getBalance());
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }
}
