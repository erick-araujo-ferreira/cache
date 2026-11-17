package com.cache.cache.controller;

import com.cache.cache.dto.ConteudoRequestDto;
import com.cache.cache.dto.ConteudoResponseDto;
import com.cache.cache.entity.Conteudo;
import com.cache.cache.mapper.ConteudoMapper;
import com.cache.cache.service.ConteudoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/conteudos")
public class ConteudoController {
    private final ConteudoService service;
    private final ConteudoMapper mapper;

    public ConteudoController(ConteudoService service, ConteudoMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConteudoResponseDto> buscarPorId(@PathVariable Long id) {
        Conteudo conteudo = service.buscarPorId(id);
        return ResponseEntity.ok()
                .header("X-Cache", service.getUltimoStatusCache())
                .body(mapper.toResponse(conteudo));
    }

    @PostMapping
    public ResponseEntity<ConteudoResponseDto> criar(@RequestBody ConteudoRequestDto request) {
        Conteudo conteudo = service.salvar(mapper.toEntity(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(conteudo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConteudoResponseDto> atualizar(@PathVariable Long id, @RequestBody ConteudoRequestDto request) {
        Conteudo conteudo = mapper.toEntity(request);
        Conteudo atualizado = service.atualizar(id, conteudo);
        return ResponseEntity.ok(mapper.toResponse(atualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
