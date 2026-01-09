package com.jdiassdev.wheredidmymoneygo.feature.transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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

        public record ListUserTransactionsRequest(
                        Long category) {
        }

        public record TotalResumeTransactionsRequest(
                        Long category_id) {
        }

        public record UpdateTransactionRequest(
                        String description,
                        BigDecimal amount,
                        Long category_id) {
        }

        /* ========= RESPONSES ========= */

        public record GetByIdResponse(Long id, String name, String email) {
        }

        public record CreateResponse(
                        Long id,
                        String description,
                        String category,
                        BigDecimal amount,
                        LocalDateTime created_at,
                        String message) {
        }

        public record ListUserTransactionsResponse(
                        Long id,
                        String description,
                        BigDecimal amount,
                        String category,
                        LocalDateTime created_at

        ) {
        }

        public record TotalResumeTransactionsResponse(
                        BigDecimal total_amount,
                        Long total_itens,
                        BigDecimal min_amount,
                        BigDecimal max_amount) {
        }

        public record UpdateResponse(
                        String description,
                        String category,
                        BigDecimal amount) {
        }

        public record StatusResponse(
                        Long id,
                        String description) {
        }

}
