package com.cache.cache.mapper;

import com.cache.cache.dto.ConteudoRequestDto;
import com.cache.cache.dto.ConteudoResponseDto;
import com.cache.cache.entity.Conteudo;
import org.springframework.stereotype.Component;

@Component
public class ConteudoMapper {
    public Conteudo toEntity(ConteudoRequestDto dto) {
        Conteudo conteudo = new Conteudo();
        conteudo.setTitulo(dto.titulo());
        conteudo.setCorpo(dto.corpo());
        conteudo.setAutor(dto.autor());
        return conteudo;
    }

    public ConteudoResponseDto toResponse(Conteudo conteudo) {
        return new ConteudoResponseDto(
                conteudo.getId(),
                conteudo.getTitulo(),
                conteudo.getCorpo(),
                conteudo.getAutor(),
                conteudo.getAtualizadoEm()
        );
    }
}
