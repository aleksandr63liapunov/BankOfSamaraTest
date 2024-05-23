package com.example.bankofsamaratest.repository;

import com.example.bankofsamaratest.model.PhoneNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhoneNumberRepository extends JpaRepository<PhoneNumber, Long> {

    boolean existsByNumber(String phoneNumber);
}
