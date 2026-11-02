
package com.academy.banking.transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transaction {

    private final TransactionType type;
    private final BigDecimal amount;
    private final LocalDateTime createdAt;

    public Transaction(
            TransactionType type,
            BigDecimal amount
    ) {
        if (type == null) {
            throw new IllegalArgumentException(
                    "İşlem türü boş olamaz."
            );
        }

        if (amount == null ||
                amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                    "İşlem tutarı pozitif olmalıdır."
            );
        }

        this.type = type;
        this.amount = amount;
        this.createdAt = LocalDateTime.now();
    }

    public TransactionType getType() {
        return type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }


}
