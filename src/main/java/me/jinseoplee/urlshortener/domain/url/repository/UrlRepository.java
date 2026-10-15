package me.jinseoplee.urlshortener.domain.url.repository;

import me.jinseoplee.urlshortener.domain.url.entity.Url;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UrlRepository extends JpaRepository<Url, Long> {
}
