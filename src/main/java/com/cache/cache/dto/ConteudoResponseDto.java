package com.cache.cache.dto;

import java.time.LocalDateTime;

public record ConteudoResponseDto(Long id, String titulo, String corpo, String autor, LocalDateTime atualizadoEm) {
}
