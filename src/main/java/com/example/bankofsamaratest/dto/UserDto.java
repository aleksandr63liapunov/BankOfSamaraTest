package com.example.bankofsamaratest.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;
    private Set<String> phoneNumber;
    private String login;
    private String password;
    private Set<String> email;
    private BigDecimal balance;
}
