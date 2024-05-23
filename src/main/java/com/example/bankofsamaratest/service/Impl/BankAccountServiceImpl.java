package com.example.bankofsamaratest.service.Impl;

import com.example.bankofsamaratest.model.BankAccount;
import com.example.bankofsamaratest.model.User;
import com.example.bankofsamaratest.repository.BankAccountRepository;
import com.example.bankofsamaratest.repository.UserRepository;
import com.example.bankofsamaratest.service.BankAccountService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.PeriodicTrigger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class BankAccountServiceImpl implements BankAccountService {

    private final BankAccountRepository bankAccountRepository;
    private final UserRepository userRepository;
    private ThreadPoolTaskScheduler taskScheduler;
    private ScheduledFuture<?> scheduledFuture;

    @Autowired
    public BankAccountServiceImpl(BankAccountRepository bankAccountRepository, UserRepository userRepository) {
        this.bankAccountRepository = bankAccountRepository;
        this.userRepository = userRepository;
        this.taskScheduler = new ThreadPoolTaskScheduler();
        this.taskScheduler.initialize();
    }

    @PostConstruct
    public void startSchedulerAutomatically() {
        startScheduler();
    }

    @Override
    @Transactional
    public void updateBalance() {
        log.info("Starting balance update ");
        List<User> userList = userRepository.findAll();
        List<BankAccount> bankAccountList = userList.stream()
                .map(User::getAccount)
                .toList();

        boolean shouldStopScheduler = false;
        for (BankAccount account : bankAccountList) {
            BigDecimal balance = account.getBalance();
            BigDecimal newBalance = balance.multiply(BigDecimal.valueOf(1.05));
            BigDecimal initialDeposit = account.getInitialDeposit();
            BigDecimal maxBalance = initialDeposit.multiply(BigDecimal.valueOf(2.07));

            if (newBalance.compareTo(maxBalance) <= 0) {
                account.setBalance(newBalance);
                shouldStopScheduler = false;
            } else {
                shouldStopScheduler = true;
                account.setBalance(maxBalance);
            }
        }
        bankAccountRepository.saveAll(bankAccountList);

        if (shouldStopScheduler && scheduledFuture != null && !scheduledFuture.isCancelled()) {
            log.warn("Max balance reached, stop the scheduler");
            scheduledFuture.cancel(false);
        } else {
            log.info("Balance update successfully");
        }
    }

    public String startScheduler() {
        if (scheduledFuture == null || scheduledFuture.isCancelled()) {
            scheduledFuture = taskScheduler.schedule(
                    this::updateBalance,
                    new PeriodicTrigger(60, TimeUnit.SECONDS));
            log.info("Scheduler started successfully.");
            return "Scheduler started successfully.";
        } else {
            log.warn("Scheduler already running.");
            return "Scheduler is already running.";
        }
    }

    @Override
    public synchronized boolean transferMoney(Long fromUserId, Long toUserId, BigDecimal sumForTransfer) {
        log.info("Money transfer from user with ID {} to user with ID {} for sum of transfer {}", fromUserId, toUserId, sumForTransfer);
        User userFrom = userRepository.findById(fromUserId).orElseThrow(() -> new IllegalArgumentException("Not found userFrom"));
        User userTo = userRepository.findById(toUserId).orElseThrow(() -> new IllegalArgumentException("Not found userTo"));


        synchronized (this) {
            BankAccount fromAccount = userFrom.getAccount();
            BankAccount toAccount = userTo.getAccount();

            if (fromAccount.withdraw(sumForTransfer)) {
                toAccount.deposit(sumForTransfer);
                bankAccountRepository.save(fromAccount);
                bankAccountRepository.save(toAccount);
                log.info("Money transfer completed successfully");
                return true;
            } else {
                log.warn("Insufficient funds for transfer");
                return false;
            }
        }
    }
}



