package com.krayc.repository;

import com.krayc.model.BankAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface BankAccountRepository extends JpaRepository<BankAccountEntity, Integer> {

    @Modifying
    @Transactional
    @Query("update BankAccountEntity bae set bae.balance=:qBalance where bae.bankAccount=:qBankAccount")
    public void updateBalance(@Param("qBalance") Double qBalance, @Param("qBankAccount") String qBankAccount);

    public BankAccountEntity findByBankAccount(String bankAccount);

    @Modifying
    @Transactional
    @Query("update BankAccountEntity bae set bae.bankAccount=:qBankAccount where bae.bid=:oBid")
    public void updateAccount(@Param("qBankAccount") String qBankAccount, @Param("oBid") Integer oBid);

}
