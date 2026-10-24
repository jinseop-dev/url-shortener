package me.jinseoplee.urlshortener.url.repository;

import me.jinseoplee.urlshortener.url.entity.Url;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UrlRepository extends JpaRepository<Url, Long> {
}
