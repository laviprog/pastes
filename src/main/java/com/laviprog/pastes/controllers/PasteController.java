package com.laviprog.pastes.controllers;

import com.laviprog.pastes.dto.pastes.CreatePasteRequest;
import com.laviprog.pastes.dto.pastes.PasteResponse;
import com.laviprog.pastes.exceptions.PasteWithThatTitleAlreadyExistsException;
import com.laviprog.pastes.exceptions.ResourceNotFoundException;
import com.laviprog.pastes.models.Paste;
import com.laviprog.pastes.models.User;
import com.laviprog.pastes.services.PasteService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pastes")
@RequiredArgsConstructor
@Tag(name = "Pastes")
public class PasteController {
    private final PasteService pasteService;
    @GetMapping
    public List<PasteResponse> getAllPastes() {
        return pasteService
                .getAllPastes()
                .stream()
                .map(PasteResponse::new)
                .toList();
    }

    @GetMapping("/top")
    public List<PasteResponse> getPastesTop(@RequestParam(defaultValue = "1") int start, @RequestParam(defaultValue = "6") int end) {
        return pasteService
                .getTopPastes(start, end)
                .stream()
                .map(PasteResponse::new)
                .toList();
    }

    @GetMapping("/{id}")
    public PasteResponse getPaste(@PathVariable Long id, @AuthenticationPrincipal User user) {
        return new PasteResponse(pasteService.getPasteById(id, user));
    }

    @GetMapping("/{author}/{title}")
    public PasteResponse getPasteByAuthorAndTitle(@PathVariable String author,
                                                  @PathVariable String title,
                                                  @AuthenticationPrincipal User user) {
        return new PasteResponse(pasteService.getPasteByAuthorAndTitle(author, title, user));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public PasteResponse createPaste(
            @ModelAttribute CreatePasteRequest createPasteRequest,
            @AuthenticationPrincipal User user) throws PasteWithThatTitleAlreadyExistsException{
        return new PasteResponse(pasteService.createPaste(createPasteRequest, user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePasteById(@PathVariable Long id, @AuthenticationPrincipal User user) {
        Paste paste = pasteService.getPasteById(id, null);
        if (paste != null) {
            pasteService.deletePaste(paste, user);
            return ResponseEntity.noContent().build();
        } else {
            throw new ResourceNotFoundException(String.format("Paste with id %s not found", id));
        }
    }

    @DeleteMapping("/{author}/{title}")
    public ResponseEntity<Void> deletePasteByAuthorAndTitle(@PathVariable String author,
                                                            @PathVariable String title,
                                                            @AuthenticationPrincipal User user) {
        Paste paste = pasteService.getPasteByAuthorAndTitle(author, title, null);
        if (paste != null) {
            pasteService.deletePaste(paste, user);
            return ResponseEntity.noContent().build();
        } else {
            throw new ResourceNotFoundException(String.format("Paste with author %s and title %s not found", author, title));
        }
    }

    //public PasteResponse updatePaste(@RequestBody UpdatePasteRequest request, @AuthenticationPrincipal User user) {

}
