package com.example.bankofsamaratest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BankOfSamaraTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(BankOfSamaraTestApplication.class, args);
    }

}
