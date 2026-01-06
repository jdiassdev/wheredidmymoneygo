package com.jdiassdev.wheredidmymoneygo.dto;

import java.math.BigDecimal;

public record TransactionTotals(
        Long totalItems,
        BigDecimal totalAmount,
        BigDecimal minAmount,
        BigDecimal maxAmount) {
}
