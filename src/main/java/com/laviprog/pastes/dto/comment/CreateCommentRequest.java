package com.laviprog.pastes.dto.comment;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema
public class CreateCommentRequest {
    private String body;
    private Long pasteId;
}
