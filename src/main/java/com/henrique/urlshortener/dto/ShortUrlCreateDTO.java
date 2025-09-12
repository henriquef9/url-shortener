package com.henrique.urlshortener.dto;

import java.time.OffsetDateTime;

public record ShortUrlCreateDTO(
        Long id,
        String originalUrl,
        String shortCode,
        OffsetDateTime createdAt,
        OffsetDateTime expiresAt
) {
}
