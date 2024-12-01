package com.laviprog.pastes.controllers;

import com.laviprog.pastes.dto.comment.CommentResponse;
import com.laviprog.pastes.dto.comment.CreateCommentRequest;
import com.laviprog.pastes.dto.comment.UpdateCommentRequest;
import com.laviprog.pastes.models.Comment;
import com.laviprog.pastes.models.User;
import com.laviprog.pastes.services.CommentService;
import com.laviprog.pastes.services.PasteService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
@Tag(name = "Comments")
public class CommentController {
    private final CommentService commentService;
    private final PasteService pasteService;

    @PostMapping
    public CommentResponse createComment(@RequestBody @Valid CreateCommentRequest comment, @AuthenticationPrincipal User user) {
        return new CommentResponse(
                commentService.save(
                        new Comment(
                                comment.getBody(),
                                user.getUsername(),
                                pasteService.getPasteById(comment.getPasteId(), null),
                                user
                        )
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id, @AuthenticationPrincipal User user) {
        commentService.deleteCommentById(id, user);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public CommentResponse getCommentById(@PathVariable Long id) {
        return new CommentResponse(commentService.getCommentById(id));
    }

    @PatchMapping
    public CommentResponse updateCommentBody(@RequestBody @Valid UpdateCommentRequest comment, @AuthenticationPrincipal User user) {
        return new CommentResponse(commentService.updateComment(comment, user));
    }
}
