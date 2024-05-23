package com.example.bankofsamaratest.controller;

import com.example.bankofsamaratest.service.BankAccountService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@Tag(name = "Transfer ")
@RestController
@RequestMapping("/api/transfer_money")
@AllArgsConstructor
public class BankController {
    private final BankAccountService bankAccountService;

    @PutMapping("/start")
    public boolean transfer(@RequestParam Long fromId,@RequestParam Long toId,
                         @RequestParam BigDecimal sumTrans) {
       return bankAccountService.transferMoney(fromId, toId, sumTrans);
    }
}

