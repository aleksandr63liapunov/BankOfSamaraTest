package com.example.bankofsamaratest.service.Impl;

import com.example.bankofsamaratest.model.BankAccount;
import com.example.bankofsamaratest.model.Email;
import com.example.bankofsamaratest.model.PhoneNumber;
import com.example.bankofsamaratest.model.User;
import com.example.bankofsamaratest.repository.EmailRepository;
import com.example.bankofsamaratest.repository.PhoneNumberRepository;
import com.example.bankofsamaratest.repository.UserRepository;
import com.example.bankofsamaratest.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Service
@Slf4j
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final EmailRepository emailRepository;
    private final PhoneNumberRepository phoneNumberRepository;
    private PasswordEncoder encoder;
    @Transactional
    public User createUser(User user) {
        log.info("Creating user: {}", user);
        if (userRepository.existsByLogin(user.getLogin())) {
            throw new IllegalArgumentException("Login already use");
        }
        Set<PhoneNumber> phones = new HashSet<>();
        for (PhoneNumber phone : user.getPhones()) {
            if (phoneNumberRepository.existsByNumber(phone.getNumber())) {
                throw new IllegalArgumentException("Phone number " + phone.getNumber() + " already in use");
            }
            phone.setUser(user);
            phones.add(phone);
        }
        user.setPhones(phones);

        Set<Email> emails = new HashSet<>();
        for (Email email : user.getEmails()) {
            if (emailRepository.existsByEmail(email.getEmail())) {
                throw new IllegalArgumentException("Email already exist");
            }
            email.setUser(user);
            emails.add(email);
        }
        user.setEmails(emails);

        BankAccount bankAccount = user.getAccount();
        if (bankAccount == null || bankAccount.getBalance() == null) {
            throw new IllegalArgumentException("Bank account and its balance cannot be null");
        }
        if (bankAccount.getBalance().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Initial balance cannot be negative");
        }
        bankAccount.setUser(user);
        user.setAccount(bankAccount);
        user.setPassword(encoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);
        log.info("User created successfully: {}", savedUser);
        return savedUser;
    }

}
