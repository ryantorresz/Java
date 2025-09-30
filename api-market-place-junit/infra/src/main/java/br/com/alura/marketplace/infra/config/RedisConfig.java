package br.com.alura.marketplace.infra.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@EnableRedisRepositories(basePackages = "br.com.alura")
@Configuration
public class RedisConfig {

}
