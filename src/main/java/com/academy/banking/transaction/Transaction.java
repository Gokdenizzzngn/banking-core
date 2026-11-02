
package com.academy.banking.transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class Transaction {

    private final TransactionType type;
    private final BigDecimal amount;
    private final LocalDateTime createdAt;
    private final UUID transferId;

    public Transaction(
            TransactionType type,
            BigDecimal amount
    ) {
        this(type, amount, null);
    }

    public Transaction(
            TransactionType type,
            BigDecimal amount,
            UUID transferId
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

        boolean isTransfer =
                type == TransactionType.TRANSFER_IN ||
                        type == TransactionType.TRANSFER_OUT;

        if (isTransfer && transferId == null) {
            throw new IllegalArgumentException(
                    "Transfer kimliği boş olamaz."
            );
        }

        if (!isTransfer && transferId != null) {
            throw new IllegalArgumentException(
                    "Normal işlem transfer kimliği taşıyamaz."
            );
        }

        this.type = type;
        this.amount = amount;
        this.createdAt = LocalDateTime.now();
        this.transferId = transferId;
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

    public UUID getTransferId() {
        return transferId;
    }

}
