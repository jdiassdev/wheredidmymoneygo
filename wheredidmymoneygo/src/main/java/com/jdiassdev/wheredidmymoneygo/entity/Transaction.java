package com.jdiassdev.wheredidmymoneygo.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "CHAR(36)")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotBlank
    private String description;

    @Positive
    private BigDecimal amount = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    private TransactionType type; // NORMAL | CARO

    @Column(name = "category")
    private String category; // tipo: alimentação, transporte, etc.

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public enum TransactionType {
        NORMAL, CARO
    }

    public void updateType() {
        if (amount.compareTo(user.getExpensiveThreshold()) >= 0) {
            type = TransactionType.CARO;
        } else {
            type = TransactionType.NORMAL;
        }
    }

}
