package com.jdiassdev.wheredidmymoneygo.feature.user;

import java.util.UUID;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UserDTO {

        public record CreateRequest(
                        @NotBlank String name,
                        @Email String email,
                        @NotBlank String password) {
        }

        public record CreateResponse(
                        UUID id,
                        String name,
                        String email) {
        }

        public record getByIdResponse(
                        UUID id,
                        String name,
                        String email) {
        }
}
