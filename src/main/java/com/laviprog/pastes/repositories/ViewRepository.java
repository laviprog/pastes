package com.laviprog.pastes.repositories;

import com.laviprog.pastes.models.View;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ViewRepository extends JpaRepository<View, Long> {
    Optional<View> findByPasteIdAndUserId(Long pasteId, Long userId);
    void deleteAllByPasteId(Long pasteId);

    void deleteAllByUserId(Long userId);
}
