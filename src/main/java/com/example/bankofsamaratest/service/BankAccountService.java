package com.example.bankofsamaratest.service;

import java.math.BigDecimal;

public interface BankAccountService {
    void updateBalance();
    String startScheduler();
    boolean transferMoney(Long fromUser,Long toUser, BigDecimal sumForTransfer);
}
