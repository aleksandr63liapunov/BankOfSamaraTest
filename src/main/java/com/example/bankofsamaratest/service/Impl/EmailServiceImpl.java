package com.example.bankofsamaratest.service.Impl;

import com.example.bankofsamaratest.model.Email;
import com.example.bankofsamaratest.model.User;
import com.example.bankofsamaratest.repository.EmailRepository;
import com.example.bankofsamaratest.repository.UserRepository;
import com.example.bankofsamaratest.service.EmailService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@AllArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final EmailRepository emailRepository;
    private final UserRepository userRepository;

    @Transactional
    public Email addEmail(Long userId, String email) {
        log.info("Add email {} for user with ID {}", email, userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("user not found"));
        if (emailRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email already exists for another user");
        }
        Email addEmail = new Email();
        addEmail.setUser(user);
        addEmail.setEmail(email);
        emailRepository.save(addEmail);
        log.info("Email {} added successfully for user with ID {}", email, userId);
        return addEmail;
    }

    @Transactional
    public Email updateEmail(Long userId, Long emailId, String newEmail) {
        log.info("Updating email with ID {} for user with ID {} to new email {}", emailId, userId, newEmail);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("user not found"));
        Email updateEmail = user.getEmails()
                .stream()
                .filter(e -> e.getId().equals(emailId))
                .findFirst().orElseThrow(() -> new IllegalArgumentException("email not found"));
        if (user.getEmails()
                .stream()
                .anyMatch(p -> !p.getId().equals(emailId) && p.getEmail().equals(newEmail))) {
            throw new IllegalArgumentException("email  already exists for this user");
        }
        updateEmail.setEmail(newEmail);
        emailRepository.save(updateEmail);
        log.info("Email updated successfully for user with ID {}", userId);
        return updateEmail;
    }
    @Transactional
    public void deleteEmail(Long userId, Long emailId) {
        log.info("Deleting email with ID {} for user with ID {}", emailId, userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("user not found"));
        Email email = emailRepository.findById(emailId)
                .orElseThrow(() -> new IllegalArgumentException("email not found"));
        if (user.getEmails().size() <= 1) {
            throw new IllegalStateException("Cannot delete the last email of the user");
        }

        if (!user.getEmails().contains(email)) {
            throw new IllegalArgumentException("Email does not belong to the user");
        }
        user.getEmails().remove(email);

        emailRepository.delete(email);
        log.info("Email with ID {} deleted successfully for user with ID {}", emailId, userId);
    }
}

