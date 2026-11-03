package me.jinseoplee.urlshortener.url.presentation;

import me.jinseoplee.urlshortener.global.exception.BusinessException;
import me.jinseoplee.urlshortener.global.exception.ErrorCode;
import me.jinseoplee.urlshortener.url.application.RedirectUrlUseCase;
import me.jinseoplee.urlshortener.url.application.ShortenUrlUseCase;
import me.jinseoplee.urlshortener.url.presentation.dto.UrlShortenRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UrlController.class)
class UrlControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ShortenUrlUseCase shortenUrlUseCase;

    @MockitoBean
    private RedirectUrlUseCase redirectUrlUseCase;

    @Test
    void shorten() throws Exception {
        // given
        String originalUrl = "https://example.com";
        String shortKey = "abc";

        UrlShortenRequest request = new UrlShortenRequest(originalUrl);

        given(shortenUrlUseCase.execute(originalUrl))
                .willReturn(shortKey);

        // when & then
        mockMvc.perform(post("/api/urls")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.shortKey").value(shortKey));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "",
            " ",
            "example.com",
            "https://",
            "https://exam ple.com",
    })
    void shorten_invalidOriginalUrl_returnsBadRequest(String originalUrl) throws Exception {
        // given
        UrlShortenRequest request = new UrlShortenRequest(originalUrl);

        ErrorCode errorCode = ErrorCode.INVALID_INPUT_VALUE;

        // when & then
        mockMvc.perform(post("/api/urls")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(errorCode.getCode()))
                .andExpect(jsonPath("$.message").value(errorCode.getMessage()))
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors").isNotEmpty());
    }

    @Test
    void redirect() throws Exception {
        // given
        String shortKey = "abc";
        String originalUrl = "https://example.com";

        given(redirectUrlUseCase.execute(shortKey)).willReturn(originalUrl);

        // when & then
        mockMvc.perform(get("/{shortKey}", shortKey))
                .andExpect(status().isFound())
                .andExpect(header().string("Location", originalUrl));
    }

    @Test
    void redirect_notFound_returnsNotFound() throws Exception {
        // given
        String shortKey = "10000000";

        ErrorCode errorCode = ErrorCode.URL_NOT_FOUND;
        given(redirectUrlUseCase.execute(shortKey)).willThrow(new BusinessException(errorCode));

        // when & then
        mockMvc.perform(get("/{shortKey}", shortKey))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(errorCode.getCode()))
                .andExpect(jsonPath("$.message").value(errorCode.getMessage()));
    }
}