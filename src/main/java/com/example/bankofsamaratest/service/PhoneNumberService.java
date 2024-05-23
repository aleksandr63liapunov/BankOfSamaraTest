package com.example.bankofsamaratest.service;

import com.example.bankofsamaratest.model.PhoneNumber;

public interface PhoneNumberService {
    PhoneNumber addPhoneNumber(Long userId, String phoneNumber);
    PhoneNumber updatePhone(Long userId, Long phoneId, String phoneNumber);
    void deleteNumber(Long userId, Long phoneId);
}
