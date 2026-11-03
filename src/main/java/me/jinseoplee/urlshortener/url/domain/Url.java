package me.jinseoplee.urlshortener.url.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.jinseoplee.urlshortener.global.entity.BaseTimeEntity;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Url extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 2048)
    private String originalUrl;

    @Column(unique = true, length = 7)
    private String shortKey;

    private long redirectCount;

    public Url(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public void assignShortKey(String shortKey) {
        this.shortKey = shortKey;
    }

    public void increaseRedirectCount() {
        this.redirectCount++;
    }
}
