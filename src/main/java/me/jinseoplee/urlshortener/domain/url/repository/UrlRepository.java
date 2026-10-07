package me.jinseoplee.urlshortener.domain.url.repository;

import me.jinseoplee.urlshortener.domain.url.entity.Url;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UrlRepository extends JpaRepository<Url, Long> {

    Optional<Url> findByShortKey(String shortKey);
}
