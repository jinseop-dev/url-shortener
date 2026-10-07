package me.jinseoplee.urlshortener.domain.url.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.jinseoplee.urlshortener.domain.url.dto.UrlCreateRequest;
import me.jinseoplee.urlshortener.domain.url.dto.UrlCreateResponse;
import me.jinseoplee.urlshortener.domain.url.service.UrlService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UrlController {

    private final UrlService urlService;

    @PostMapping("/api/urls")
    public ResponseEntity<UrlCreateResponse> create(@Valid @RequestBody UrlCreateRequest request) {
        UrlCreateResponse response = urlService.create(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}
