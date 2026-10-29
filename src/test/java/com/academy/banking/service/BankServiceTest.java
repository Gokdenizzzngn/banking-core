
package com.academy.banking.service;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BankServiceTest {

    private BankService bankService;

    @BeforeEach
    void setUp() {

        bankService = new BankService();

        bankService.createAccount("TR001", "Ali");
        bankService.createAccount("TR002", "Ayşe");
    }

    @Test
    void shouldDepositMoneySuccessfully() {


        //  Test edilecek işlemi gerçekleştir
        bankService.deposit(
                "TR001",
                new BigDecimal("1000.00")
        );

        //  Sonucu kontrol et
        BigDecimal balance =
                bankService.findAccount("TR001").getBalance();

        assertEquals(
                0,
                balance.compareTo(new BigDecimal("1000.00"))
        );
    }

    @Test
    void shouldRejectWithdrawalWhenBalanceIsInsufficient() {

        bankService.deposit(
                "TR001",
                new BigDecimal("500.00")
        );

        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> bankService.withdraw(
                                "TR001",
                                new BigDecimal("1000.00")
                        )
                );

        assertEquals(
                "Yetersiz bakiye.",
                exception.getMessage()
        );

        BigDecimal balance =
                bankService.findAccount("TR001").getBalance();

        assertEquals(
                0,
                balance.compareTo(new BigDecimal("500.00"))
        );
    }

    @Test
    void shouldTransferMoneySuccessfully() {

        bankService.deposit(
                "TR001",
                new BigDecimal("1000.00")
        );

        bankService.deposit(
                "TR002",
                new BigDecimal("2000.00")
        );

        bankService.transfer(
                "TR001",
                "TR002",
                new BigDecimal("300.00"));

        assertEquals(
                new BigDecimal("700.00"),
                bankService.findAccount("TR001").getBalance()
        );

        assertEquals(
                new BigDecimal("2300.00"),
                bankService.findAccount("TR002").getBalance()
        );
    }

    @Test
    void shouldNotChangeBalancesWhenTransferFails() {

        bankService.deposit(
                "TR001",
                new BigDecimal("1000.00")
        );

        bankService.deposit(
                "TR002",
                new BigDecimal("2000.00")
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> bankService.transfer(
                        "TR001",
                        "TR002",
                        new BigDecimal("5000.00")
                )
        );

        assertEquals(
                new BigDecimal("1000.00"),
                bankService.findAccount("TR001").getBalance()
        );

        assertEquals(
                new BigDecimal("2000.00"),
                bankService.findAccount("TR002").getBalance()
        );

    }

}
