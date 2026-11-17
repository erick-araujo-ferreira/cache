package com.cache.cache.service;

import com.cache.cache.cache.ConteudoCache;
import com.cache.cache.dto.CacheEstatisticasResponseDto;
import com.cache.cache.entity.Conteudo;
import com.cache.cache.repository.ConteudoRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class ConteudoService {
    private final ConteudoRepository repository;
    private final ConteudoCache cache;

    public ConteudoService(ConteudoRepository repository, ConteudoCache cache) {
        this.repository = repository;
        this.cache = cache;
    }

    @Transactional
    public Conteudo salvar(Conteudo conteudo) {
        Conteudo salvo = repository.save(conteudo);
        cache.salvar(salvo);
        return salvo;
    }

    @Transactional
    public Conteudo atualizar(Long id, Conteudo dados) {
        Conteudo existente = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Conteúdo não encontrado"));

        existente.setTitulo(dados.getTitulo());
        existente.setCorpo(dados.getCorpo());
        existente.setAutor(dados.getAutor());

        Conteudo atualizado = repository.save(existente);
        cache.remover(id);
        return atualizado;
    }

    @Transactional
    public void excluir(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Conteúdo não encontrado");
        }

        repository.deleteById(id);
        cache.remover(id);
    }

    @Transactional
    public Conteudo buscarPorId(Long id) {
        Conteudo conteudoEmCache = cache.buscar(id);
        if (conteudoEmCache != null) {
            return conteudoEmCache;
        }

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        Conteudo conteudo = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Conteúdo não encontrado"));
        cache.salvar(conteudo);
        return conteudo;
    }

    public CacheEstatisticasResponseDto estatisticasCache() {
        return cache.estatisticas();
    }

    public void limparCache() {
        cache.limpar();
    }

    public String getUltimoStatusCache() {
        return cache.getUltimoStatus();
    }
}
