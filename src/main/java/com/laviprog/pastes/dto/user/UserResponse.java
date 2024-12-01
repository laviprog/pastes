package com.laviprog.pastes.dto.user;

import com.laviprog.pastes.dto.comment.CommentResponse;
import com.laviprog.pastes.dto.pastes.PasteResponse;
import com.laviprog.pastes.models.User;
import com.laviprog.pastes.models.enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Collections;
import java.util.List;

@Data
@Schema
public class UserResponse {
    private Long id;

    private String username;

    private String email;

    private Role role;

    private List<PasteResponse> pastes;

    private List<CommentResponse> comments;

    public UserResponse(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.role = user.getRole();
        this.pastes = user.getPastes() == null ? Collections.emptyList() : user.getPastes().stream().map(PasteResponse::new).toList();
        this.comments = user.getComments() == null ? Collections.emptyList() : user.getComments().stream().map(CommentResponse::new).toList();
    }
}
