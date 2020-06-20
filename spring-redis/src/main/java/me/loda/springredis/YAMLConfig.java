package me.loda.springredis;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.List;

@Data
@Configuration
@EnableConfigurationProperties
@PropertySource("classpath:application.yml")
@ConfigurationProperties(prefix = "redis")
public class YAMLConfig {
    private String redisMaster;
    private String redisPassword;
    private List<RedisInfo> node;
}
