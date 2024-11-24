package com.laviprog.pastes.dto.pastes;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Schema
public class CreatePasteRequest {
    @Schema(description = "Title", example = "Paste#1")
    @Size(min = 3, max = 1000)
    @NotBlank(message = "Title cannot be empty")
    private String title;

    @Schema(description = "Body paste", example = "This is my PASTE!!!")
    @Size(max = 60000)
    private String body;

    private List<String> categories;
    private List<String> tags;
    private String format = "TEXT";

    private List<MultipartFile> files;
}
