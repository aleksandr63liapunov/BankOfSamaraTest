package com.example.bankofsamaratest.repository;

import com.example.bankofsamaratest.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByLogin(String login);

    @Query("SELECT COUNT(u) > 0 FROM User u WHERE u.login = :login")
    boolean existsByLogin(@Param("login") String login);

    @Query("SELECT u FROM User u JOIN PhoneNumber pn ON u.id = pn.user.id WHERE pn.number = :phone")
    Page<User> searchByPhone(@Param("phone") String phone, Pageable pageable);

    @Query("SELECT u FROM User u WHERE u.dateOfBirth > :date_of_birth")
    Page<User> searchByDateOfBirth(@Param("date_of_birth") LocalDate date, Pageable pageable);

    @Query("SELECT u FROM User u WHERE u.fullName LIKE CONCAT(:strPrefix, '%')")
    Page<User> searchUserByNameStartWith(@Param("strPrefix") String str, Pageable pageable);

    @Query("SELECT u FROM User u JOIN Email e ON u.id = e.user.id WHERE e.email = :email")
    Page<User> searchByEmail(@Param("email") String email, Pageable pageable);


}

