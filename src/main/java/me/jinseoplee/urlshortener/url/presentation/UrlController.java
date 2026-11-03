package me.jinseoplee.urlshortener.url.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.jinseoplee.urlshortener.url.application.RedirectUrlUseCase;
import me.jinseoplee.urlshortener.url.application.ShortenUrlUseCase;
import me.jinseoplee.urlshortener.url.presentation.dto.UrlShortenRequest;
import me.jinseoplee.urlshortener.url.presentation.dto.UrlShortenResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RequiredArgsConstructor
@RestController
public class UrlController {

    private final ShortenUrlUseCase shortenUrlUseCase;
    private final RedirectUrlUseCase redirectUrlUseCase;

    @PostMapping("/api/urls")
    public ResponseEntity<UrlShortenResponse> shorten(
            @Valid @RequestBody UrlShortenRequest request
    ) {
        String shortKey = shortenUrlUseCase.execute(request.originalUrl());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new UrlShortenResponse(shortKey));
    }

    @GetMapping("/{shortKey}")
    public ResponseEntity<Void> redirect(@PathVariable String shortKey) {
        String originalUrl = redirectUrlUseCase.execute(shortKey);

        return ResponseEntity
                .status(HttpStatus.FOUND)
                .location(URI.create(originalUrl))
                .build();
    }
}
