package com.laviprog.pastes.dto.user;

import com.laviprog.pastes.models.enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema
public class CreateUserRequest {

    @Schema(description = "Username", example = "Jon")
    @Size(min = 3, max = 50)
    @NotBlank(message = "Username cannot be empty")
    private String username;

    @Schema(description = "Password", example = "my_1secret1_password")
    @Size(min = 6, max = 255)
    @NotBlank(message = "Password cannot be empty")
    private String password;

    @Schema(description = "Email", example = "jondoe@gmail.com")
    @Size(min = 5, max = 255)
    @NotBlank(message = "Email cannot be empty")
    private String email;

    @Schema(description = "Role", example = "ADMIN")
    private Role role = Role.USER;

}
