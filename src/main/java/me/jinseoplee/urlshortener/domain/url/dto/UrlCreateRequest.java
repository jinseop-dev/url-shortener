package me.jinseoplee.urlshortener.domain.url.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UrlCreateRequest(

        @NotBlank(message = "URL은 필수 입력값입니다.")
        @Size(max = 2048, message = "URL은 2048자를 초과할 수 없습니다.")
        @Pattern(
                regexp = "^https?://\\S+$",
                message = "올바른 URL 형식이 아닙니다."
        )
        String originalUrl
) {
}
