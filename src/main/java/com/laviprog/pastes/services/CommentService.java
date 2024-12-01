package com.laviprog.pastes.services;

import com.laviprog.pastes.dto.comment.UpdateCommentRequest;
import com.laviprog.pastes.exceptions.CustomAccessDeniedException;
import com.laviprog.pastes.exceptions.ResourceNotFoundException;
import com.laviprog.pastes.models.Comment;
import com.laviprog.pastes.models.User;
import com.laviprog.pastes.models.enums.Role;
import com.laviprog.pastes.repositories.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    public Comment getCommentById(Long commentId) throws ResourceNotFoundException {
        return commentRepository
                .findById(commentId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("The comment was not found")
                );
    }

    public void deleteCommentById(Long commentId, User user) throws ResourceNotFoundException, CustomAccessDeniedException {

        Comment comment = getCommentById(commentId);

        if (user.getRole() == Role.ADMIN || comment.getUser().getId().equals(user.getId())) {
            commentRepository.delete(comment);
        } else {
            throw new CustomAccessDeniedException("You do not have permission to delete this object");
        }
    }

    public Comment updateComment(UpdateCommentRequest commentRequest, User user) throws ResourceNotFoundException, CustomAccessDeniedException {

        Comment comment = getCommentById(commentRequest.getId());

        if (user.getRole() == Role.ADMIN || comment.getUser().getId().equals(user.getId())) {
            comment.setBody(commentRequest.getBody());
            return commentRepository.save(comment);
        } else {
            throw new CustomAccessDeniedException("You do not have permission to update this object");
        }
    }
}
