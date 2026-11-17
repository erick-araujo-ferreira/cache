package com.cache.cache.cache;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app.cache")
public class CacheProperties {
    private Integer maxItens;
    private Integer ttlSegundos;

    public Integer getMaxItens() {
        return maxItens;
    }

    public void setMaxItens(Integer maxItens) {
        this.maxItens = maxItens;
    }

    public Integer getTtlSegundos() {
        return ttlSegundos;
    }

    public void setTtlSegundos(Integer ttlSegundos) {
        this.ttlSegundos = ttlSegundos;
    }
}
