package com.example.bankofsamaratest.service.Impl;

import com.example.bankofsamaratest.model.PhoneNumber;
import com.example.bankofsamaratest.model.User;
import com.example.bankofsamaratest.repository.PhoneNumberRepository;
import com.example.bankofsamaratest.repository.UserRepository;
import com.example.bankofsamaratest.service.PhoneNumberService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@AllArgsConstructor
public class PhoneNumberServiceImpl implements PhoneNumberService {

    private final PhoneNumberRepository phoneNumberRepository;
    private final UserRepository userRepository;

    @Transactional
    public PhoneNumber addPhoneNumber(Long userId, String phoneNumber) {
        log.info("Add phone {} for user with ID {}", phoneNumber, userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("user not found"));
        if (phoneNumberRepository.existsByNumber(phoneNumber)) {
            throw new IllegalArgumentException("Phone already exists for another user");
        }
        PhoneNumber addPhoneNumber = new PhoneNumber();
        addPhoneNumber.setUser(user);
        addPhoneNumber.setNumber(phoneNumber);
        phoneNumberRepository.save(addPhoneNumber);
        log.info("Phone {} added successfully for user with ID {}", phoneNumber, userId);
        return addPhoneNumber;
    }

    @Transactional
    public PhoneNumber updatePhone(Long userId, Long phoneId, String phoneNumber) {
        log.info("Updating Phone with ID {} for user with ID {} to new email {}", phoneId, userId, phoneNumber);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        PhoneNumber phoneToUpdate = user.getPhones().stream()
                .filter(e -> e.getId().equals(phoneId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("phone not found"));
        if (user.getPhones().stream().anyMatch(p -> !p.getId().equals(phoneId) && p.getNumber().equals(phoneNumber))) {
            throw new IllegalArgumentException("Phone number already exists for this user");
        }
        phoneToUpdate.setNumber(phoneNumber);
        userRepository.save(user);
        log.info("Phone updated successfully for user with ID {}", userId);
        return phoneToUpdate;
    }

    @Transactional
    public void deleteNumber(Long userId, Long phoneId) {
        log.info("Deleting phone with ID {} for user with ID {}", phoneId, userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        PhoneNumber phoneNumber = phoneNumberRepository.findById(phoneId)
                .orElseThrow(() -> new IllegalArgumentException("Phone not found"));
        if (user.getPhones().size() <= 1 || !user.getPhones().contains(phoneNumber)) {
            throw new IllegalStateException("Cannot delete the last phone of the user or phone does not belong to the user");
        }
        user.getPhones().remove(phoneNumber);
        phoneNumberRepository.delete(phoneNumber);
        log.info("Phone with ID {} deleted successfully for user with ID {}", phoneId, userId);
    }
}
