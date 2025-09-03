package com.henrique.urlshortener.exception;

import java.time.LocalDateTime;

public record HttpError(
        int status,
        String message,
        LocalDateTime timestamp
) {
}
