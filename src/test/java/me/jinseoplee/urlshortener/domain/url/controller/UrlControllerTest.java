package me.jinseoplee.urlshortener.domain.url.controller;

import me.jinseoplee.urlshortener.domain.url.dto.UrlCreateRequest;
import me.jinseoplee.urlshortener.domain.url.dto.UrlCreateResponse;
import me.jinseoplee.urlshortener.domain.url.service.UrlService;
import me.jinseoplee.urlshortener.global.error.ErrorCode;
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
    private UrlService urlService;

    @Test
    void create_success() throws Exception {
        // given
        UrlCreateRequest request = new UrlCreateRequest("https://example.com");
        UrlCreateResponse response = new UrlCreateResponse("1");

        given(urlService.create(request))
                .willReturn(response);

        // when & then
        mockMvc.perform(post("/api/urls")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.shortKey").value("1"));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "",
            "http:// ",
            "https:// ",
            "http:/example.com",
            "https:/example.com",
            "ftp://example.com",
            "example.com"
    })
    void create_invalid_returnsBadRequest(String originalUrl) throws Exception {
        // given
        UrlCreateRequest request = new UrlCreateRequest(originalUrl);
        ErrorCode errorCode = ErrorCode.INVALID_INPUT_VALUE;

        // when & then
        mockMvc.perform(post("/api/urls")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(errorCode.getCode()))
                .andExpect(jsonPath("$.message").value(errorCode.getMessage()))
                .andExpect(jsonPath("$.errors[0].field").value("originalUrl"))
                .andExpect(jsonPath("$.errors[0].value").exists())
                .andExpect(jsonPath("$.errors[0].reason").exists());
    }
}