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

    public record CompleteDataRequest(
            String email,
            BigDecimal monthlySalary,
            BigDecimal expensiveThreshold) {
    }

    /* ========= RESPONSES ========= */

    public record GetByIdResponse(Long id, String name, String email) {
    }

    public record CreateResponse(Long id, String name, String email) {
    }

    public record LoginResponse(String token, String name) {
    }

    public record CompleteDataResponse(String email) {
    }
}
