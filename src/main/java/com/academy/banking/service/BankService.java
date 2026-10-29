
package com.academy.banking.service;

import com.academy.banking.model.BankAccount;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class BankService {

    private final Map<String, BankAccount> accounts =
            new HashMap<>();

    public void createAccount(
            String accountNumber,
            String ownerName
    ) {

        if (accounts.containsKey(accountNumber)) {
            throw new IllegalArgumentException(
                    "Bu hesap numarası zaten kullanılıyor."
            );
        }

        BankAccount account =
                new BankAccount(accountNumber, ownerName);

        accounts.put(accountNumber, account);
    }

    public BankAccount findAccount(String accountNumber) {

        BankAccount account = accounts.get(accountNumber);

        if (account == null) {
            throw new IllegalArgumentException(
                    "Hesap bulunamadı: " + accountNumber
            );
        }

        return account;
    }

    public void deposit(
            String accountNumber,
            BigDecimal amount
    ) {

        BankAccount account = findAccount(accountNumber);

        account.deposit(amount);
    }

    public void withdraw(
            String accountNumber,
            BigDecimal amount
    ) {

        BankAccount account = findAccount(accountNumber);

        account.withdraw(amount);
    }


    public void transfer(
            String fromAccount,
            String toAccount,
            BigDecimal amount
    ) {

        // 1. Gönderen ve alıcı hesapları bul
        BankAccount sender = findAccount(fromAccount);
        BankAccount receiver = findAccount(toAccount);

        // 2. Aynı hesaba transferi engelle
        if (fromAccount.equals(toAccount)) {
            throw new IllegalArgumentException(
                    "Aynı hesaba transfer yapılamaz."
            );
        }

        // 3. Transfer tutarını kontrol et
        if (amount == null ||
                amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                    "Transfer tutarı sıfırdan büyük olmalıdır."
            );
        }

        // 4. Gönderenin bakiyesini kontrol et
        if (sender.getBalance().compareTo(amount) < 0) {
            throw new IllegalArgumentException(
                    "Transfer için yetersiz bakiye."
            );
        }

        // 5. Para transferini gerçekleştir
        sender.withdraw(amount);
        receiver.deposit(amount);
    }


}
