package com.laviprog.pastes.dto.user;

import com.laviprog.pastes.models.enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema
public class UpdateUserRoleRequest {
    private Long id;

    @Schema(description = "Role", example = "ADMIN")
    private Role role;
}
