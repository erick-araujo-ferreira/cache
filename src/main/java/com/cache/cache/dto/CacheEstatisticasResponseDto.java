package com.cache.cache.dto;

public record CacheEstatisticasResponseDto(Integer tamanho, Integer limite, Integer hits, Integer misses, Integer evictions, Double hitRatio) {
}
