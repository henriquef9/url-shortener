package com.henrique.urlshortener.repository;

import com.henrique.urlshortener.model.ShortUrlAccess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShortUrlAccessRepository extends JpaRepository<ShortUrlAccess, Long> {

    List<ShortUrlAccess> findByShortUrlId(Long shortUrlId);

    Optional<ShortUrlAccess> findByAccessDateAndShortUrlId(LocalDate accessDate, Long shortUrlId);

}
