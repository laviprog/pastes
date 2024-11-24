package com.laviprog.pastes.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema
public class SignInRequest {
    @Schema(description = "Username", example = "Jon")
    @Size(min = 3, max = 50)
    @NotBlank(message = "Username cannot be empty")
    private String username;

    @Schema(description = "Password", example = "my_1secret1_password")
    @Size(min = 6, max = 255)
    @NotBlank(message = "Password cannot be empty")
    private String password;
}
