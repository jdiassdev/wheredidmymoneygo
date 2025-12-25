package com.jdiassdev.wheredidmymoneygo.feature.user;

public record UserDTO(
                Long id,
                String name,
                String email) {

        public record GetByIdResponse(Long id, String name, String email) {
        }

        public record CreateRequest(String name, String email, String password) {
        }

        public record CreateResponse(Long id, String name, String email) {
        }

        public record LoginRequest(String email, String password) {
        }

        public record LoginResponse(String email) {
        }
}