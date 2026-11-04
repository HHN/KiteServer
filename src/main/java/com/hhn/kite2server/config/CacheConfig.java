package com.hhn.kite2server.config;

import javax.cache.CacheManager;
import javax.cache.configuration.MutableConfiguration;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Creates the "buckets" cache used by the Bucket4j rate limiting filters.
 * Idempotent by design: the Caffeine JCache provider shares one CacheManager
 * per JVM, so repeated context starts (e.g. multiple test contexts) must not
 * try to create the cache twice.
 */
@Configuration
public class CacheConfig {

    @Bean
    public JCacheManagerCustomizer bucket4jCacheCustomizer() {
        return cacheManager -> {
            if (cacheManager.getCache("buckets") == null) {
                cacheManager.createCache("buckets", new MutableConfiguration<>());
            }
        };
    }
}