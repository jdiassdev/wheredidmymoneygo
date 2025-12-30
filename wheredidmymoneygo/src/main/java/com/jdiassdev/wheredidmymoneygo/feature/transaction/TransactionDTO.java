package com.jdiassdev.wheredidmymoneygo.feature.transaction;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public final class TransactionDTO {

    private TransactionDTO() {
    }

    public record CreateRequest(

            @NotNull(message = "Valor é obrigatório") @Positive(message = "Valor deve ser maior que zero") BigDecimal amount,

            @NotNull(message = "Categoria é obrigatória") Long category_id,

            @NotBlank(message = "Descrição é obrigatória") String description) {
    }

    /* ========= RESPONSES ========= */

    public record GetByIdResponse(Long id, String name, String email) {
    }

    public record CreateResponse(BigDecimal amount, String message) {
    }

}
