package com.example.url_shortener.dto;

import java.time.LocalDateTime;

public record CreateUrlRequest(String originalUrl,
                               LocalDateTime expireAt
) {
}
