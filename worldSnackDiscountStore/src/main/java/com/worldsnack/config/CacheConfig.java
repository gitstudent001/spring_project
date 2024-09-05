package com.worldsnack.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class CacheConfig {

  @Bean
  public Cache<String, Boolean> viewCache() {
    return Caffeine.newBuilder()
        .expireAfterWrite(24, TimeUnit.HOURS)  // 24시간 후 캐시 만료
        .maximumSize(10000)  // 최대 캐시 크기 설정
        .build();
  }
}
