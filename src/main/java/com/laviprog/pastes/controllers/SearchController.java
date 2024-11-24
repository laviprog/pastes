package com.laviprog.pastes.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pastes")
@RequiredArgsConstructor
@Tag(name = "Search")
public class SearchController {
}
