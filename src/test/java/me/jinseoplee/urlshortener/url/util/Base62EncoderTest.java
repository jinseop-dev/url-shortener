package me.jinseoplee.urlshortener.url.util;

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
            "62, 10"
    })
    void encode(Long id, String expected) {
        // when
        String actual = Base62Encoder.encode(id);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void encode_null_throwsException() {
        // when & then
        assertThatThrownBy(() -> Base62Encoder.encode(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ID must not be null");
    }

    @Test
    void encode_negative_throwsException() {
        // when & then
        assertThatThrownBy(() -> Base62Encoder.encode(-1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ID cannot be negative");
    }
}