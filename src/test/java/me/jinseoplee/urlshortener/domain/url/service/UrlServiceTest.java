package me.jinseoplee.urlshortener.domain.url.service;

import me.jinseoplee.urlshortener.domain.url.dto.UrlCreateRequest;
import me.jinseoplee.urlshortener.domain.url.dto.UrlCreateResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UrlServiceTest {

    @Autowired
    private UrlService urlService;

    @Test
    void create_success() {
        // given
        UrlCreateRequest request = new UrlCreateRequest("https://example.com");

        // when
        UrlCreateResponse response = urlService.create(request);

        // then
        assertThat(response.shortKey()).isEqualTo("1");
    }
}