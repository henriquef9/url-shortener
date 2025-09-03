package com.henrique.urlshortener.repository;

import com.henrique.urlshortener.model.ShortUrl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShortUrlRepository extends JpaRepository<ShortUrl, Long> {

    public Optional<ShortUrl> findByShortCode(String shortCode);

    public boolean existsByShortCode(String shortCode);

}
