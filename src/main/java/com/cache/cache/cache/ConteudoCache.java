package com.cache.cache.cache;

import com.cache.cache.dto.CacheEstatisticasResponseDto;
import com.cache.cache.entity.Conteudo;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ConteudoCache {
    private static final Logger log = LoggerFactory.getLogger(ConteudoCache.class);

    private final Map<Long, EntradaCache> cache = new HashMap<>();
    private final CacheProperties properties;
    private Integer hits = 0;
    private Integer misses = 0;
    private Integer evictions = 0;
    private String ultimoStatus = "AINDA NÃO ACESSADO";

    public ConteudoCache(CacheProperties properties) {
        this.properties = properties;
    }

    public Conteudo buscar(Long id) {
        long inicio = System.currentTimeMillis();
        EntradaCache entrada = cache.get(id);

        if (entrada == null) {
            misses++;
            ultimoStatus = "MISS";
            log.info("ConteudoCache : Cache MISS - id={} - {} ms", id, System.currentTimeMillis() - inicio);
            return null;
        }

        if (foiExpirado(entrada)) {
            cache.remove(id);
            misses++;
            ultimoStatus = "MISS";
            log.info("ConteudoCache : Cache MISS - id={} - {} ms", id, System.currentTimeMillis() - inicio);
            return null;
        }

        entrada.setUltimoAcessoEm(LocalDateTime.now());
        hits++;
        ultimoStatus = "HIT";
        log.info("ConteudoCache : Cache HIT  - id={} - {} ms", id, System.currentTimeMillis() - inicio);
        return entrada.getConteudo();
    }

    public void salvar(Conteudo conteudo) {
        if (conteudo == null || conteudo.getId() == null) {
            return;
        }

        if (cache.size() >= properties.getMaxItens() && !cache.containsKey(conteudo.getId())) {
            Long idMaisAntigo = cache.entrySet()
                    .stream()
                    .min(Comparator.comparing(entry -> entry.getValue().getUltimoAcessoEm()))
                    .map(Map.Entry::getKey)
                    .orElse(null);

            if (idMaisAntigo != null) {
                cache.remove(idMaisAntigo);
                evictions++;
            }
        }

        cache.put(conteudo.getId(), new EntradaCache(conteudo, LocalDateTime.now(), LocalDateTime.now()));
    }

    public void remover(Long id) {
        if (id != null) {
            cache.remove(id);
        }
    }

    public void limpar() {
        cache.clear();
    }

    public CacheEstatisticasResponseDto estatisticas() {
        Integer total = hits + misses;
        Double hitRatio = total == 0 ? 0.0 : (hits.doubleValue() * 100.0) / total;

        return new CacheEstatisticasResponseDto(
                cache.size(),
                properties.getMaxItens(),
                hits,
                misses,
                evictions,
                hitRatio
        );
    }

    public String getUltimoStatus() {
        return ultimoStatus;
    }

    private boolean foiExpirado(EntradaCache entrada) {
        long segundos = Duration.between(entrada.getArmazenadoEm(), LocalDateTime.now()).getSeconds();
        return segundos > properties.getTtlSegundos();
    }
}
