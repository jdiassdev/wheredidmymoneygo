package com.jdiassdev.wheredidmymoneygo.feature.user;

import java.math.BigDecimal;

public final class UserDTO {

    private UserDTO() {
    }

    /* ========= REQUESTS ========= */

    public record CreateRequest(String name, String email, String password) {
    }

    public record LoginRequest(String email, String password) {
    }

    public record PatchDataRequest(
            String name,
            BigDecimal monthly_salary,
            BigDecimal expensive_threshold) {
    }

    /* ========= RESPONSES ========= */

    public record GetByIdResponse(
            String name,
            String email,
            BigDecimal monthly_salary,
            BigDecimal expensive_threshold) {
    }

    public record CreateResponse(Long id, String name, String email) {
    }

    public record LoginResponse(String token, String name) {
    }

    public record PatchDataResponse(
            String name,
            String email,
            BigDecimal monthly_salary,
            BigDecimal expensive_threshold) {
    }
}
