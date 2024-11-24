package com.laviprog.pastes.dto.file;

import com.laviprog.pastes.models.FileEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema
public class FileResponse {

    private String filename;

    private String url;

    public FileResponse(FileEntity fileEntity) {
        this.filename = fileEntity.getFilename();
        this.url = fileEntity.getUrl();
    }

}
