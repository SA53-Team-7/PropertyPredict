package com.team7.propertypredict.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableRedisRepositories
public class RedisConfig {
	
    @Bean
    public JedisConnectionFactory connectionFactory() {
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setHostName("20.124.252.164"); // 20.124.252.164
        configuration.setPort(7000); // 7000 // 6379
        configuration.setPassword("team_seven");
        return new JedisConnectionFactory(configuration);
    }

    @Bean
    public RedisTemplate<String, Object> template() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory());

        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        // key use String serialization
        template.setKeySerializer(stringRedisSerializer);
        // hash key use String serialization
        template.setHashKeySerializer(stringRedisSerializer);
        // value use jackson serialization
        template.setValueSerializer(stringRedisSerializer);
        // hash value use jackson serialization
        template.setHashValueSerializer(stringRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }
   
}