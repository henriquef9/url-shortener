package com.henrique.urlshortener.repository;

import com.henrique.urlshortener.model.ShortUrl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ShortUrlRepository extends JpaRepository<ShortUrl, Long> {

    public Optional<ShortUrl> findByShortCode(String shortCode);

    public boolean existsByShortCode(String shortCode);

    List<ShortUrl> findShortUrlByExpiresAtBetween(OffsetDateTime expiresAtAfter, OffsetDateTime expiresAtBefore);

    public void deleteByExpiresAtBefore(OffsetDateTime expiresAtBefore);
}
