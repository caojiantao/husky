package cn.caojiantao.husky.redis;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author caojiantao
 * @since 2018-10-03 13:52:23
 */
@Slf4j
@Data
@Configuration
@ConfigurationProperties("redis")
public class RedisConfig {

    private List<SerializerConfig> serializerConfig;

    @Bean
    @SuppressWarnings("unchecked")
    RedisCacheManager cacheManager(RedisConnectionFactory factory) {
        Map<String, RedisCacheConfiguration> configurationMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(serializerConfig)) {
            RedisCacheConfiguration configuration = RedisCacheConfiguration.defaultCacheConfig();
            ProtoStuffSerializer serializer;
            for (SerializerConfig config : serializerConfig) {
                try {
                    Class<?> clazz = Class.forName(config.getClassType());
                    serializer = new ProtoStuffSerializer(clazz);
                    configuration = configuration.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(serializer));
                    configurationMap.put(config.getCacheName(), configuration);
                } catch (ClassNotFoundException e) {
                    log.error("添加redis缓存序列化映射关系失败：{}", e);
                }
            }
        }
        return RedisCacheManager.builder(factory)
                .withInitialCacheConfigurations(configurationMap)
                .build();
    }
}
