package com.laviprog.pastes.dto.pastes;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema
public class UpdatePasteRequest {
    private Long id;

    private String title;

    private String body;

    private List<String> categories;

    private List<String> tags;

    private String format;
}
