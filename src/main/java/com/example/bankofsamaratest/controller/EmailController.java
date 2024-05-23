package com.example.bankofsamaratest.controller;

import com.example.bankofsamaratest.model.Email;
import com.example.bankofsamaratest.service.EmailService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Email")
@RestController
@RequestMapping("/api/contact")
public class EmailController {

    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/add_email")
    public ResponseEntity<Email> addEmail(@RequestParam Long userId,
                                          @RequestParam String email) {
        Email addEmail = emailService.addEmail(userId, email);
        return new ResponseEntity<>(addEmail, HttpStatus.CREATED);

    }

    @PutMapping("/change_email")
    public ResponseEntity<Email> updateEmail(@RequestParam Long userId,
                                             @RequestParam Long emailId,
                                             @RequestParam String newEmail) {
        Email updateEmail = emailService.updateEmail(userId, emailId, newEmail);
        return new ResponseEntity<>(updateEmail, HttpStatus.CREATED);
    }
    @DeleteMapping("/delete_email/{userId}/{emailId}")
    public ResponseEntity<Void> deleteEmail(
            @PathVariable Long userId,
            @PathVariable Long emailId) {
        emailService.deleteEmail(userId, emailId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
