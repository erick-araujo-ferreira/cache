package com.cache.cache.cache;

import com.cache.cache.entity.Conteudo;
import java.time.LocalDateTime;

public class EntradaCache {
    private Conteudo conteudo;
    private LocalDateTime armazenadoEm;
    private LocalDateTime ultimoAcessoEm;

    public EntradaCache(Conteudo conteudo, LocalDateTime armazenadoEm, LocalDateTime ultimoAcessoEm) {
        this.conteudo = conteudo;
        this.armazenadoEm = armazenadoEm;
        this.ultimoAcessoEm = ultimoAcessoEm;
    }

    public Conteudo getConteudo() {
        return conteudo;
    }

    public void setConteudo(Conteudo conteudo) {
        this.conteudo = conteudo;
    }

    public LocalDateTime getArmazenadoEm() {
        return armazenadoEm;
    }

    public void setArmazenadoEm(LocalDateTime armazenadoEm) {
        this.armazenadoEm = armazenadoEm;
    }

    public LocalDateTime getUltimoAcessoEm() {
        return ultimoAcessoEm;
    }

    public void setUltimoAcessoEm(LocalDateTime ultimoAcessoEm) {
        this.ultimoAcessoEm = ultimoAcessoEm;
    }
}
