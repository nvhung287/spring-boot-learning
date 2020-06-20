package me.loda.springredis;
/*******************************************************
 * For Vietnamese readers:
 *    Các bạn thân mến, mình rất vui nếu project này giúp 
 * ích được cho các bạn trong việc học tập và công việc. Nếu 
 * bạn sử dụng lại toàn bộ hoặc một phần source code xin để 
 * lại dường dẫn tới github hoặc tên tác giá.
 *    Xin cảm ơn!
 *******************************************************/

import io.lettuce.core.ReadFrom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.HashSet;
import java.util.Set;

/**
 * Copyright 2019 {@author Loda} (https://loda.me).
 * This project is licensed under the MIT license.
 *
 * @since 5/4/2019
 * Github: https://github.com/loda-kun
 */
@Configuration
public class RedisConfig {

    @Autowired
    private YAMLConfig yamlConfig;

//    @Bean
//    public RedisConnectionFactory jedisConnectionFactory() {
//        RedisSentinelConfiguration sentinelConfig = new RedisSentinelConfiguration()
//                .master("mymaster")
//                .sentinel("10.22.7.111", 26379)
//                .sentinel("10.22.7.112", 26379);
//        return new JedisConnectionFactory(sentinelConfig);
//    }

    private Set makeConfigRedis() {
        Set<RedisNode> nodes = new HashSet<>();
        for (RedisInfo redisInfo : yamlConfig.getNode()) {
            nodes.add(new RedisNode(redisInfo.getHost(), redisInfo.getPort()));
        }
        return nodes;
    }


    @Bean
    public RedisConnectionFactory lettuceConnectionFactory() {

        LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder()
                .readFrom(ReadFrom.REPLICA)
                .build();
        RedisSentinelConfiguration serverSentinelConfig = new RedisSentinelConfiguration()
                .master(yamlConfig.getRedisMaster());

        serverSentinelConfig.setSentinels(makeConfigRedis());
        LettuceConnectionFactory factory = new LettuceConnectionFactory(serverSentinelConfig, clientConfig);
        factory.setDatabase(5);
        factory.setPassword("testredis@123");
        return factory;
    }
//
//    @Bean
//    public LettuceConnectionFactory redisConnectionFactory() {
//        // Tạo Standalone Connection tới Redis
//        LettuceConnectionFactory factory = new LettuceConnectionFactory(new RedisStandaloneConfiguration(redisHost, redisPort));
//        factory.setDatabase(5);
//        factory.setPassword("testredis@123");
//        return factory;
//    }

    @Bean
    @Primary
    public RedisTemplate<?, ?> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        // tạo ra một RedisTemplate
        // Với Key là Object
        // Value là Object
        // RedisTemplate giúp chúng ta thao tác với Redis
        RedisTemplate<?, ?> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.setHashKeySerializer(new GenericJackson2JsonRedisSerializer());
        template.afterPropertiesSet();
        return template;
    }

}
