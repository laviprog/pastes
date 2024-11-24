package com.laviprog.pastes.services;

import com.laviprog.pastes.dto.pastes.CreatePasteRequest;
import com.laviprog.pastes.exceptions.CustomAccessDeniedException;
import com.laviprog.pastes.exceptions.PasteWithThatTitleAlreadyExistsException;
import com.laviprog.pastes.exceptions.ResourceNotFoundException;
import com.laviprog.pastes.models.Paste;
import com.laviprog.pastes.models.User;
import com.laviprog.pastes.repositories.PasteRepository;
import com.laviprog.pastes.services.files.FileDeletionEvent;
import com.laviprog.pastes.services.files.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class PasteService {
    private final PasteRepository repository;
    private final ViewService viewService;
    private final FileService fileService;
    private final ApplicationEventPublisher eventPublisher;

    public List<Paste> getAllPastes() {
        return repository.findAll();
    }

    public List<Paste> getTopPastes(int start, int end) {
        return repository.findTopPastes(end - start, start - 1);
    }

    public Paste getPasteById(Long id, User user) {

        Paste paste = repository.findById(id).orElse(null);

        Long userId = user != null ? user.getId() : null;

        if (userId != null && paste != null) {
            if (viewService.getView(paste.getId(), userId) == null) {
                if (viewService.addView(paste.getId(), userId) != null) {
                    paste.incrementNumberViews();
                    repository.save(paste);
                }
            }
        } else if (paste == null) {
            throw new ResourceNotFoundException(String.format("Paste with id=%s was not found.", id));
        }

        return paste;

    }

    public Paste getPasteByAuthorAndTitle(String author, String title, User user) {

        Paste paste = repository.findPasteByAuthorAndTitle(author, title).orElse(null);

        Long userId = user != null ? user.getId() : null;

        if (userId != null && paste != null) {
            if (viewService.addView(paste.getId(), userId) != null) {
                paste.incrementNumberViews();
                repository.save(paste);
            }
        } else if (paste == null) {
            throw new ResourceNotFoundException(String.format("Paste with author=%s and title=%s was not found.", author, title));
        }

        return paste;

    }

    public Paste createPaste(CreatePasteRequest createPasteRequest, User user) throws PasteWithThatTitleAlreadyExistsException {

        if (getPasteByAuthorAndTitle(user.getUsername(), createPasteRequest.getTitle(), null) == null) {
            Paste paste = repository.save(new Paste(createPasteRequest, user));
            paste.setFiles(createPasteRequest.getFiles().stream().map(file -> fileService.createFile(file, paste)).toList());
            paste.incrementNumberViews();
            viewService.addView(paste.getId(), user.getId());
            return paste;
        } else {
            throw new PasteWithThatTitleAlreadyExistsException();
        }

    }

    @Transactional
    public void deletePaste(Paste paste, User user) {

        log.debug("Deleting paste with id: {}", paste.getId());

        checkAccessRights(paste, user);

        repository.delete(paste);

        viewService.deleteAllViewsByPasteId(paste.getId());

        eventPublisher.publishEvent(new FileDeletionEvent(this, paste.getFiles()));

    }

    public void checkAccessRights(Paste paste, User user) {
        if (!Objects.equals(paste.getUser().getId(), user.getId())) {
            throw new CustomAccessDeniedException("You do not have permission to delete this item.");
        }
    }
}
