package com.jun.portalservice.domain.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.spring.data.connection.RedissonConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import java.io.IOException;

@Configuration
public class RedisConfig {

  @Bean
  public RedissonConnectionFactory redissonConnectionFactory(RedissonClient redisson) {
    return new RedissonConnectionFactory(redisson);
  }

  @Bean(destroyMethod = "shutdown")
  public RedissonClient redisson(@Value("classpath:/redisson.yml") Resource configFile)
      throws IOException {
    Config config = Config.fromYAML(configFile.getInputStream());
    RedissonClient redisson = Redisson.create(config);
    return redisson;
  }

  @Bean
  public RedisTemplate<Object, Object> redisTemplate(
      RedissonConnectionFactory redissonConnectionFactory) {
    RedisTemplate<Object, Object> template = new RedisTemplate<>();
    template.setConnectionFactory(redissonConnectionFactory);
    template.setEnableTransactionSupport(true);
    template.setKeySerializer(new Jackson2JsonRedisSerializer<>(Object.class));
    template.setValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
    return template;
  }
}
