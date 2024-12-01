package com.laviprog.pastes.dto.pastes;

import com.laviprog.pastes.dto.comment.CommentResponse;
import com.laviprog.pastes.dto.file.FileResponse;
import com.laviprog.pastes.models.Paste;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Data
@Schema
public class PasteResponse {
    private Long id;

    private String title;

    private String body;

    private List<String> category;

    private List<String> tags;

    private String format;

    private Long views;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private String author;

    private List<CommentResponse> comments;

    private List<FileResponse> files;

    public PasteResponse(Paste paste) {
        this.id = paste.getId();
        this.title = paste.getTitle();
        this.body = paste.getBody();
        this.category = paste.getCategories();
        this.tags = paste.getTags();
        this.format = paste.getFormat();
        this.views = paste.getViews();
        this.createdAt = paste.getCreatedAt();
        this.updatedAt = paste.getUpdatedAt();
        this.author = paste.getAuthor();
        this.comments = paste.getComments() == null ? Collections.emptyList() : paste.getComments().stream().map(CommentResponse::new).toList();
        this.files = paste.getFiles() == null ? Collections.emptyList() : paste.getFiles().stream().map(FileResponse::new).toList();
    }

}
