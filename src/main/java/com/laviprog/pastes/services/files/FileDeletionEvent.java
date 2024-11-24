package com.laviprog.pastes.services.files;

import com.laviprog.pastes.models.FileEntity;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.List;

@Getter
public class FileDeletionEvent extends ApplicationEvent {
    private final List<FileEntity> files;

    public FileDeletionEvent(Object source, List<FileEntity> files) {
        super(source);
        this.files = files;
    }
}
