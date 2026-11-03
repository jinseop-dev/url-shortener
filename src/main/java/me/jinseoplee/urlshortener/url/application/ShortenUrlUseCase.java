package me.jinseoplee.urlshortener.url.application;

import lombok.RequiredArgsConstructor;
import me.jinseoplee.urlshortener.url.domain.Url;
import me.jinseoplee.urlshortener.url.domain.UrlRepository;
import me.jinseoplee.urlshortener.url.util.Base62Encoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ShortenUrlUseCase {

    private final UrlRepository urlRepository;

    @Transactional
    public String execute(String originalUrl) {
        Url url = new Url(originalUrl);
        urlRepository.save(url);

        String shortKey = Base62Encoder.encode(url.getId());
        url.assignShortKey(shortKey);

        return shortKey;
    }
}
