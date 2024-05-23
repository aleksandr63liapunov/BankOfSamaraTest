package com.example.bankofsamaratest.service.Impl;

import com.example.bankofsamaratest.dto.UserDto;
import com.example.bankofsamaratest.mappers.UserMapper;
import com.example.bankofsamaratest.model.User;
import com.example.bankofsamaratest.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class SearchServiceImpl {
    private final UserRepository userRepository;
    private final UserMapper userMapper = UserMapper.INSTANCE;

    public List<UserDto> getAll() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toUserDto)
                .collect(Collectors.toList());
    }

    public Page<User> searchByPhone(String number, Pageable pageable) {
        log.info("Searching users by phone number: {}", number);

        if (number == null || number.isEmpty()) {
            throw new IllegalArgumentException("Phone not be null or empty");
        }
        Page<User> userPage = userRepository.searchByPhone(number, pageable);
        if (userPage == null || userPage.isEmpty()) {
            throw new NoSuchElementException("No users found with phone");
        }
        log.info("Found {} users with phone number: {}", userPage.getTotalElements(), number);
        return userPage;
    }

    public Page<User> searchByDateOfBirth(LocalDate date, Pageable pageable) {
        log.info("Searching users by date of birth: {}", date);

        if (date == null) {
            throw new IllegalArgumentException("Date of birth not be null");
        }
        Page<User> userPage = userRepository.searchByDateOfBirth(date, pageable);
        if (userPage == null || userPage.isEmpty()) {
            throw new NoSuchElementException("No users found with date of birth");
        }
        log.info("Found {} users with date of birth: {}", userPage.getTotalElements(), date);
        return userPage;
    }

    public Page<User> searchUserStartWith(String str, Pageable pageable) {
        log.info("Searching users by name prefix: {}", str);

        if (str == null || str.isEmpty()) {
            throw new IllegalArgumentException("Name prefix not be null or empty");
        }
        Page<User> userPage = userRepository.searchUserByNameStartWith(str, pageable);
        if (userPage == null || userPage.isEmpty()) {
            throw new NoSuchElementException("No users found with name prefix");
        }
        log.info("Found {} users with name prefix: {}", userPage.getTotalElements(), str);

        return userPage;
    }

    public Page<User> searchByEmail(String email, Pageable pageable) {
        log.info("Searching users by email: {}", email);

        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email not be null or empty");
        }
        Page<User> userPage = userRepository.searchByEmail(email, pageable);
        if (userPage == null || userPage.isEmpty()) {
            throw new NoSuchElementException("No users found with email");
        }
        log.info("Found {} users with email: {}", userPage.getTotalElements(), email);
        return userPage;
    }
}
