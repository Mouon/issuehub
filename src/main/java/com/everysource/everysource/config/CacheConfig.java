package com.everysource.everysource.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

/** Read Through와 Write Through 조합  */
@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig() /**  Redis 캐시의 기본 설정을 불러옴 */
                .entryTtl(Duration.ofMinutes(10)) /** 캐시된 데이터의 생존 시간을 10분으로 설정. 10분 지나면 캐시된 데이터는 자동으로 삭제 */
                .disableCachingNullValues() /** null 값을 캐싱하지 않도록 설정. 연산 결과가 null인 경우 캐시에 저장되지 않음 */
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer())) /**  Redis 캐시에서 사용할 키의 직렬화 방식을 설정 , String 타입을 바이트 배열로 직렬화 */
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer())); /** 객체를 JSON 형태로 직렬화하며, 역직렬화 시에는 JSON 데이터로부터 객체를 복원 */

        RedisCacheManager cacheManager = RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(config)
                .build();

        cacheManager.setTransactionAware(true); /** 트랜잭션 활성화 */
        cacheManager.afterPropertiesSet();

        return cacheManager;
    }


}
