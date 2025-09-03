package com.henrique.urlshortener.dto;

import com.henrique.urlshortener.model.ShortUrlAccess;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;

public record ShortUrlStatisticsDTO(
        Long id,
        String originalUrl,
        String shortCode,
        OffsetDateTime createdAt,
        OffsetDateTime expiresAt,
        List<ShortUrlAccess> accesses,
        Long totalAccess,
        LocalDate lastAccess
) {
}
