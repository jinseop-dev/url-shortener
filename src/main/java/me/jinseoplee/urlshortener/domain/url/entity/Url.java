package me.jinseoplee.urlshortener.domain.url.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Url {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 2048)
    private String originalUrl;

    @Column(unique = true, length = 7)
    private String shortKey;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public Url(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    public void assignShortKey(String shortKey) {
        this.shortKey = shortKey;
    }
}
