package com.laviprog.pastes.services;

import com.laviprog.pastes.models.View;
import com.laviprog.pastes.repositories.ViewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ViewService {
    private final ViewRepository repository;

    public View addView(Long pasteId, Long userId) {
        log.debug("Add view for paste id {} user id {}", pasteId, userId);
        View view = getView(pasteId, userId);
        return view == null ? repository.save(new View(pasteId, userId)) : view;
    }

    public View getView(Long pasteId, Long userId) {
        return repository.findByPasteIdAndUserId(pasteId, userId).orElse(null);
    }

    public void deleteAllViewsByPasteId(Long pasteId) {
        log.debug("Deleting all views by pasteId {}", pasteId);
        repository.deleteAllByPasteId(pasteId);
    }
}
