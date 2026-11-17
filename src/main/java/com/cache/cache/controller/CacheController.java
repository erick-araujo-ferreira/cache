package com.cache.cache.controller;

import com.cache.cache.dto.CacheEstatisticasResponseDto;
import com.cache.cache.service.ConteudoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cache")
public class CacheController {
    private final ConteudoService service;

    public CacheController(ConteudoService service) {
        this.service = service;
    }

    @GetMapping("/estatisticas")
    public ResponseEntity<CacheEstatisticasResponseDto> estatisticas() {
        return ResponseEntity.ok(service.estatisticasCache());
    }

    @DeleteMapping
    public ResponseEntity<Void> limpar() {
        service.limparCache();
        return ResponseEntity.noContent().build();
    }
}
