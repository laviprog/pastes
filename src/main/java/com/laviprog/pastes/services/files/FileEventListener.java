package com.laviprog.pastes.services.files;

import com.laviprog.pastes.storage.Storage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class FileEventListener {
    private final Storage storage;

    @EventListener
    public void handleFileDeletion(FileDeletionEvent event) {
        log.debug("File deletion event");
        event.getFiles().forEach(file -> {
            try {
                storage.deleteFile(file.getBucket(), file.getKey());
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        });
    }
}
