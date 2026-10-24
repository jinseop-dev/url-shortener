package me.jinseoplee.urlshortener.url.repository;

import me.jinseoplee.urlshortener.url.entity.Url;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UrlRepository extends JpaRepository<Url, Long> {

    Optional<Url> findByShortKey(String shortKey);
}
