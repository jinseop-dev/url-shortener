package me.jinseoplee.urlshortener.domain.url.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class Base62EncoderTest {

    @ParameterizedTest
    @CsvSource({
            "0, 0",
            "10, a",
            "35, z",
            "36, A",
            "61, Z",
            "62, 10",
            "11157, 2TX",
            "2009215674938, zn9edcu"
    })
    void encode_success(Long id, String expected) {
        // when
        String result = Base62Encoder.encode(id);

        // then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void encode_null_throwsException() {
        // when & then
        assertThatThrownBy(() -> Base62Encoder.encode(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void encode_negative_throwsException() {
        // when & then
        assertThatThrownBy(() -> Base62Encoder.encode(-1L))
                .isInstanceOf(IllegalArgumentException.class);
    }
}