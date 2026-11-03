package me.jinseoplee.urlshortener.url.application;

import lombok.RequiredArgsConstructor;
import me.jinseoplee.urlshortener.global.exception.BusinessException;
import me.jinseoplee.urlshortener.global.exception.ErrorCode;
import me.jinseoplee.urlshortener.url.domain.Url;
import me.jinseoplee.urlshortener.url.domain.UrlRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class RedirectUrlUseCase {

    private final UrlRepository urlRepository;

    @Transactional
    public String execute(String shortKey) {
        Url url = urlRepository.findByShortKey(shortKey)
                .orElseThrow(() -> new BusinessException(ErrorCode.URL_NOT_FOUND));

        url.increaseRedirectCount();

        return url.getOriginalUrl();
    }
}
