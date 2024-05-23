package com.example.bankofsamaratest.controller;

import com.example.bankofsamaratest.service.BankAccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class BankControllerTest {
    @Mock
    private BankAccountService bankAccountService;

    @InjectMocks
    private BankController bankController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(bankController).build();
    }

    @Test
    void transfer_ShouldReturnTrue_WhenTransferSuccessful() throws Exception {

        Long fromId = 1L;
        Long toId = 2L;
        BigDecimal sumTrans = BigDecimal.valueOf(50);

        when(bankAccountService.transferMoney(fromId, toId, sumTrans)).thenReturn(true);

        // Act & Assert
        mockMvc.perform(put("/api/transfer_money/start")
                        .param("fromId", fromId.toString())
                        .param("toId", toId.toString())
                        .param("sumTrans", sumTrans.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(bankAccountService, times(1)).transferMoney(fromId, toId, sumTrans);
    }

    @Test
    void transfer_ShouldReturnFalse_WhenTransferUnsuccessful() throws Exception {

        Long fromId = 1L;
        Long toId = 2L;
        BigDecimal sumTrans = BigDecimal.valueOf(50);

        when(bankAccountService.transferMoney(fromId, toId, sumTrans)).thenReturn(false);

        mockMvc.perform(put("/api/transfer_money/start")
                        .param("fromId", fromId.toString())
                        .param("toId", toId.toString())
                        .param("sumTrans", sumTrans.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(bankAccountService, times(1)).transferMoney(fromId, toId, sumTrans);
    }
}

