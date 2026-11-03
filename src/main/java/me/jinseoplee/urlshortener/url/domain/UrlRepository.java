package me.jinseoplee.urlshortener.url.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UrlRepository extends JpaRepository<Url, Long> {

    Optional<Url> findByShortKey(String shortKey);
}
