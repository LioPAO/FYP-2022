package com.finalyearproject.fyp.config.redisconfig;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@ConfigurationProperties(prefix = "myredisconfig")
@Data
public class MyRedisCacheConfigurationProperties {

    private Duration timeToLive;
    private int port;
    private String host;
    private Map<String, Duration> cacheExpirations = new HashMap<>();

    /*
    * MyRedisCacheConfigurationProperties stores all properties to be used when setting redis
    * These properties will be initialised in the application.properties or application.yml file
    * They will be initialised starting with the prefix "redis"
    * Example redis.port, redis.host, redis.timeToLive
    * cacheExpirations holds the name of a cache in its string (Key) and the corresponding duration(TTL) in seconds
    * */
}
