package com.laviprog.pastes.dto.comment;

import com.laviprog.pastes.models.Comment;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema
public class CommentResponse {
    private Long id;

    private String body;

    private String author;

    private LocalDateTime createdAt;

    public CommentResponse(Comment comment) {
        this.id = comment.getId();
        this.body = comment.getBody();
        this.author = comment.getAuthor();
        this.createdAt = comment.getCreatedAt();
    }

}
