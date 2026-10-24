package me.jinseoplee.urlshortener.url.entity;

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

    @Column(nullable = false)
    private long clickCount = 0L;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    public Url(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public void assignShortKey(String shortKey) {
        this.shortKey = shortKey;
    }

    public void increaseClickCount() {
        this.clickCount++;
    }
}
