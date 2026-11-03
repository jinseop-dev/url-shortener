package me.jinseoplee.urlshortener.url.presentation;

import me.jinseoplee.urlshortener.global.exception.ErrorCode;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UrlController.class)
class UrlControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ShortenUrlUseCase shortenUrlUseCase;

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
}