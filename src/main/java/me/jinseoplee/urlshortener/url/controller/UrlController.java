package me.jinseoplee.urlshortener.url.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.jinseoplee.urlshortener.url.dto.UrlCreateRequest;
import me.jinseoplee.urlshortener.url.dto.UrlCreateResponse;
import me.jinseoplee.urlshortener.url.service.UrlService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RequiredArgsConstructor
@RestController
public class UrlController {

    private final UrlService urlService;

    @PostMapping("/api/urls")
    public ResponseEntity<UrlCreateResponse> create(
            @Valid @RequestBody UrlCreateRequest request
    ) {
        UrlCreateResponse response = urlService.create(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{shortKey}")
    public ResponseEntity<Void> redirect(@PathVariable String shortKey) {
        String originalUrl = urlService.getOriginalUrl(shortKey);

        return ResponseEntity
                .status(HttpStatus.FOUND)
                .location(URI.create(originalUrl))
                .build();
    }
}
