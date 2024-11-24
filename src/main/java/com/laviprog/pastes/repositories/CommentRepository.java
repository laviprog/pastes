package com.laviprog.pastes.repositories;

import com.laviprog.pastes.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
