package com.example.bankofsamaratest.service;

import com.example.bankofsamaratest.model.Email;

public interface EmailService {

    Email addEmail(Long userId, String email);
    Email updateEmail(Long userId, Long emailId, String newEmail);
    void deleteEmail(Long userId, Long emailId);
}
