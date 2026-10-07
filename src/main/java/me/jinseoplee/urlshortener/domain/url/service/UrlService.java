package me.jinseoplee.urlshortener.domain.url.service;

import lombok.RequiredArgsConstructor;
import me.jinseoplee.urlshortener.domain.url.dto.UrlCreateRequest;
import me.jinseoplee.urlshortener.domain.url.dto.UrlCreateResponse;
import me.jinseoplee.urlshortener.domain.url.entity.Url;
import me.jinseoplee.urlshortener.domain.url.repository.UrlRepository;
import me.jinseoplee.urlshortener.domain.url.util.Base62Encoder;
import me.jinseoplee.urlshortener.global.error.BusinessException;
import me.jinseoplee.urlshortener.global.error.ErrorCode;
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

    @Transactional(readOnly = true)
    public String findOriginalUrl(String shortKey) {
        Url url = urlRepository.findByShortKey(shortKey)
                .orElseThrow(() -> new BusinessException(ErrorCode.URL_NOT_FOUND));

        return url.getOriginalUrl();
    }
}
