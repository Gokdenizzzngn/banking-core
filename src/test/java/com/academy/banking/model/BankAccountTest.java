
package com.academy.banking.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.academy.banking.transaction.Transaction;
import com.academy.banking.transaction.TransactionType;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class BankAccountTest {

    private BankAccount account;

    @BeforeEach
    void setUp() {
        account = new BankAccount("TR001", "Ali");
    }

    @Test
    void shouldHaveZeroBalanceWhenCreated() {

        assertEquals(
                BigDecimal.ZERO,
                account.getBalance()
        );
    }


    @Test
    void shouldIncreaseBalanceWhenDepositingMoney() {


        BigDecimal amount = new BigDecimal("500.00");


        account.deposit(amount);


        assertEquals(
                0,
                account.getBalance().compareTo(
                        new BigDecimal("500.00")
                )
        );
    }

    @Test
    void shouldRejectNegativeDeposit() {

        assertThrows(
                IllegalArgumentException.class,
                () -> account.deposit(
                        new BigDecimal("-100.00")
                )
        );

        assertEquals(
                0,
                account.getBalance().compareTo(BigDecimal.ZERO)
        );
    }

    @Test
    void shouldDecreaseBalanceWhenWithdrawingMoney() {

        BigDecimal amount = new BigDecimal("1000.00");
        BigDecimal withdrawalAmount = new BigDecimal("300.00");

        account.deposit(amount);
        account.withdraw(withdrawalAmount);

        assertEquals(new BigDecimal("700.00"),
                account.getBalance()
        );

    }

    @Test
    void shouldRejectWithdrawalWhenBalanceIsInsufficient() {

        BigDecimal amount = new BigDecimal("500.00");
        account.deposit(amount);

        assertThrows(
                IllegalArgumentException.class,
                () -> account.withdraw(
                        new BigDecimal("1000.00")
                )
        );

        assertEquals(0,
                account.getBalance().compareTo(new BigDecimal("500.00"))
        );

    }

    @Test
    void shouldRejectNegativeInitialBalance() {

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new BankAccount(
                        "TR004",
                        "Zeynep",
                        new BigDecimal("-100.00")
                )
        );

        assertEquals(
                "Başlangıç bakiyesi negatif olamaz.",
                exception.getMessage()
        );
    }

    @Test
    void shouldRejectNullInitialBalance() {

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new BankAccount(
                        "TR005", "Can", null)
                );

        assertEquals(
                "Başlangıç bakiyesi negatif olamaz.",
                exception.getMessage()
        );
    }


    @Test
    void shouldRecordDepositTransaction() {

        // Arrange
        BigDecimal amount = new BigDecimal("500.00");

        // Act
        account.deposit(amount);

        // Assert
        assertEquals(
                1,
                account.getTransactions().size()
        );

        Transaction transaction =
                account.getTransactions().get(0);

        assertEquals(
                TransactionType.DEPOSIT,
                transaction.getType()
        );

        assertEquals(
                amount,
                transaction.getAmount()
        );
    }

    @Test
    void shouldRecordWithdrawalTransaction() {

        BigDecimal amount = new BigDecimal("1000.00");
        account.deposit(amount);
        BigDecimal amount2 = new BigDecimal("300.00");
        account.withdraw(amount2);

        assertEquals(
                2,
                account.getTransactions().size()
        );

        Transaction transaction =
                account.getTransactions().get(1);

        assertEquals(
                TransactionType.WITHDRAW,
                transaction.getType()
        );

        assertEquals(
                amount2,
                transaction.getAmount()
        );

        assertEquals(
                0,
                account.getBalance().compareTo(
                        new BigDecimal("700.00")
                )
        );
    }

    @Test
    void shouldNotRecordFailedWithdrawal() {

        BigDecimal amount = new BigDecimal("500.00");
        account.deposit(amount);
        BigDecimal amount2 = new BigDecimal("1000.00");

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> account.withdraw(amount2)
        );

        assertEquals(
                "Yetersiz bakiye.",
                exception.getMessage()
        );

        assertEquals(
                1,
                account.getTransactions().size()
        );

        Transaction transaction =
                account.getTransactions().get(0);

        assertEquals(
                TransactionType.DEPOSIT,
                transaction.getType()
        );

        assertEquals(0,
                account.getBalance().compareTo(new BigDecimal("500.00"))
        );
    }
}
