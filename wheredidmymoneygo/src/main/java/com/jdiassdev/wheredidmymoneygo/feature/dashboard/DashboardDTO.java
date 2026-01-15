package com.jdiassdev.wheredidmymoneygo.feature.dashboard;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public final class DashboardDTO {

        private DashboardDTO() {
        }

        public record MonthlyEvolutionReqDTO(
                        LocalDate date_reference) {
        }
        public record MonthSummaryReqDTO(
                        LocalDate date_reference) {
        }
        public record TopCaregoryMonthSummaryReqDTO(
                        LocalDate date_reference) {
        }

        public record MonthSummaryDTO(
                        Long totalItems,
                        BigDecimal totalAmount) {
        }

        public record MonthSummaryResponse(
                        Long total_items,
                        BigDecimal total_amount,
                        LocalDateTime start_date,
                        LocalDateTime end_date) {
        }

}