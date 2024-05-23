package com.example.bankofsamaratest.repository;

import com.example.bankofsamaratest.model.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailRepository extends JpaRepository<Email, Long> {

    boolean existsByEmail(String email);
}
