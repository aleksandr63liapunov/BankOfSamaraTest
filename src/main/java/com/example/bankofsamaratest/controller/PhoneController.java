package com.example.bankofsamaratest.controller;

import com.example.bankofsamaratest.model.PhoneNumber;
import com.example.bankofsamaratest.service.PhoneNumberService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Phone")
@RestController
@RequestMapping("/api/contact")
public class PhoneController {
    private final PhoneNumberService phoneNumberService;

    public PhoneController(PhoneNumberService phoneNumberService) {
        this.phoneNumberService = phoneNumberService;
    }

    @PostMapping("/add_phone")
    public ResponseEntity<PhoneNumber> addPhone(@RequestParam Long userId,
                                                @RequestParam String newNumber) {
        PhoneNumber addPhone = phoneNumberService.addPhoneNumber(userId, newNumber);
        return new ResponseEntity<>(addPhone, HttpStatus.CREATED);
    }

    @PutMapping("/change_phone")
    public ResponseEntity<PhoneNumber> changePhone(@RequestParam Long userId,
                                                  @RequestParam Long phoneId,
                                                  @RequestParam String newNumber) {
        PhoneNumber phoneNumber = phoneNumberService.updatePhone(userId, phoneId, newNumber);
        return new ResponseEntity<>(phoneNumber, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete_phone/{userId}/{phoneId}")
    public ResponseEntity<Void> deletePhone(@PathVariable Long userId,
                                            @PathVariable Long phoneId) {
        phoneNumberService.deleteNumber(userId, phoneId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
