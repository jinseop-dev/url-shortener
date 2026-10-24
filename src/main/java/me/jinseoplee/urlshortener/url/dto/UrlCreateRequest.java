package me.jinseoplee.urlshortener.url.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UrlCreateRequest(

        @NotBlank(message = "URL을 입력해주세요.")
        @Size(
                max = 2048,
                message = "URL은 최대 2048자까지 입력 가능합니다."
        )
        @Pattern(
                regexp = "^https?://\\S+$",
                message = "올바른 URL 형식이 아닙니다."
        )
        String originalUrl
) {
}
