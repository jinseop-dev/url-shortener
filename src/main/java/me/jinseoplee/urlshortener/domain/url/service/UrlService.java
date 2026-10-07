package me.jinseoplee.urlshortener.domain.url.service;

import lombok.RequiredArgsConstructor;
import me.jinseoplee.urlshortener.domain.url.dto.UrlCreateRequest;
import me.jinseoplee.urlshortener.domain.url.dto.UrlCreateResponse;
import me.jinseoplee.urlshortener.domain.url.entity.Url;
import me.jinseoplee.urlshortener.domain.url.repository.UrlRepository;
import me.jinseoplee.urlshortener.domain.url.util.Base62Encoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UrlService {

    private final UrlRepository urlRepository;

    @Transactional
    public UrlCreateResponse create(UrlCreateRequest request) {
        Url url = new Url(request.originalUrl());
        urlRepository.save(url);

        String shortKey = Base62Encoder.encode(url.getId());
        url.assignShortKey(shortKey);

        return new UrlCreateResponse(shortKey);
    }
}
