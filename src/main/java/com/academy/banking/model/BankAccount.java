
package com.academy.banking.model;
import com.academy.banking.transaction.Transaction;
import com.academy.banking.transaction.TransactionType;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

public class BankAccount {

    private final String accountNumber;
    private final String ownerName;
    private BigDecimal balance;
    private final List<Transaction> transactions = new ArrayList<>();

    public BankAccount(
            String accountNumber,
            String ownerName
    ) {
        this(accountNumber, ownerName, BigDecimal.ZERO);
    }

    public BankAccount(
            String accountNumber,
            String ownerName,
            BigDecimal initialBalance
    ) {

        if (accountNumber == null || accountNumber.isBlank()) {
            throw new IllegalArgumentException(
                    "Hesap numarası boş olamaz."
            );
        }

        if (ownerName == null || ownerName.isBlank()) {
            throw new IllegalArgumentException(
                    "Hesap sahibi boş olamaz."
            );
        }

        if (initialBalance == null ||
                initialBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(
                    "Başlangıç bakiyesi negatif olamaz."
            );
        }

        this.accountNumber = accountNumber;
        this.ownerName = ownerName;
        this.balance = initialBalance;
    }

    public void deposit(BigDecimal amount) {

        validateAmount(amount);

        balance = balance.add(amount);


        Transaction transaction = new Transaction(
                TransactionType.DEPOSIT,
                amount
        );

        transactions.add(transaction);
    }

    public void withdraw(BigDecimal amount) {

        validateAmount(amount);

        if (balance.compareTo(amount) < 0) {
            throw new IllegalArgumentException(
                    "Yetersiz bakiye."
            );
        }

        balance = balance.subtract(amount);

        Transaction transaction = new Transaction(
                TransactionType.WITHDRAW,
                amount
        );

        transactions.add(transaction);
    }

    private void validateAmount(BigDecimal amount) {

        if (amount == null
                || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                    "Tutar sıfırdan büyük olmalıdır."
            );
        }
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public List<Transaction> getTransactions() {
        return List.copyOf(transactions);
    }
}
