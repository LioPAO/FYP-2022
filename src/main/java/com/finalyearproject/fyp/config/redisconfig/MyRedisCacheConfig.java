package com.finalyearproject.fyp.config.redisconfig;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableConfigurationProperties(MyRedisCacheConfigurationProperties.class)
public class MyRedisCacheConfig {


    //A static method to create a RedisCacheConfiguration object with a set duration
    private static RedisCacheConfiguration createRedisCacheConfiguration(Duration timeToLive) {
        return RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(timeToLive);
    }

    //CREATE OBJECT OF redisStandaloneConfiguration AND PASS IN THE PARAMETERS FROM CONFIG PROPERTIES
    /*
     * redisStandaloneConfiguration will accept standAlone parameters which will be used by connectionFactory during connection between application and redis server
     * Parameters such as Host name, Port, Password, UserName, Database can be set in this object
     * For this project we will only initialize hostname and port name
     * In other projects the required parameters can be declared and/or initialized in MyRedisCacheConfigurationProperties.class and passed in  redisStandaloneConfiguration
     * */
    @Bean
    public LettuceConnectionFactory redisConnectionFactory(MyRedisCacheConfigurationProperties redisProperties) {

        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(redisProperties.getHost());
        redisStandaloneConfiguration.setPort(redisProperties.getPort());

        return new LettuceConnectionFactory(redisStandaloneConfiguration);
    }

    //CREATE REDIS TEMPLATE USING THE CONNECTION
    /*
     *RedisTemplate<K, V> where K is the object type of the KEY which is used by redis to identify the cacheMemory of the cacheable object
     *and V is the actual object type being stored in the cache memory
     * For this project the keys will be stored as strings and the value type will be determined by the object being stored
     **/
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory cf) {

        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(cf);
        return redisTemplate;
    }

    /*
     * RedisCacheConfiguration is a bean used to store configuration
     * Multiple configuration beans can be created each containing different configuration properties
     * These different configuration beans can then be used or paired with different cached "tables" i.e cacheable entities
     * These configuration beans will then have to be managed by a cacheManager.
     * */
    @Bean
    public RedisCacheConfiguration defaultRedisCacheConfiguration(MyRedisCacheConfigurationProperties redisProperties) {
        return createRedisCacheConfiguration(redisProperties.getTimeToLive());

    }

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory, MyRedisCacheConfigurationProperties properties) {
        Map<String, RedisCacheConfiguration> multipleCacheConfigurations = new HashMap<>();

        for (Map.Entry<String, Duration> cacheNameAndTimeToLive : properties.getCacheExpirations().entrySet()) {
            multipleCacheConfigurations.put(cacheNameAndTimeToLive.getKey(), createRedisCacheConfiguration(cacheNameAndTimeToLive.getValue()));
        }
        return RedisCacheManager
                .builder(redisConnectionFactory)
                .cacheDefaults(defaultRedisCacheConfiguration(properties))
                .withInitialCacheConfigurations(multipleCacheConfigurations).build();
    }
    /* CACHE MANAGER ----EXPLANATION
     * To build an object of type CacheManager or in simple terms a cache manager you need a
     * 1. RedisConnectionFactory and 2. Default RedisCacheConfiguration bean
     * For every new RedisCacheConfiguration to be added, a name and the corresponding configuration bean should be passed as arguments in the .withInitialCacheConfigurations
     * Example .withInitialCacheConfigurations("nameOfCacheAbleEntity",RedisCacheConfiguration.defaultCacheConfig().entryTtl("passInDuration"))
     *
     * Well the code above does the same thing.
     * It creates a map to store the "nameOfCacheAbleEntity" and its corresponding RedisCacheConfiguration object
     * It collects the data stored in MyRedisCacheConfigurationProperties
     * (remember we stored a map of string and duration which will now be used as "nameOfCacheAbleEntity" and its corresponding duration)
     * For each entry or K,V pair stored in the properties class it puts them in the created "multipleCacheConfigurations" map
     *
     * All that is left is to build the object of type CacheManager or in simple terms a cache manager
     * Luckily the withInitialCacheConfigurations method can accept the map containing the "nameOfCacheAbleEntity" and the corresponding duration and map
     *
     * In case the map is empty the .cacheDefaults method which takes a bean of RedisCacheConfiguration will be used by redis to manage the cached entities/cached tables
     * This bean can be obtained from the defaultRedisCacheConfiguration method which returns a RedisCacheConfiguration object with a timeToLive set in the application.yaml file and binded to the MyRedisCacheConfigurationProperties.class
     * */

    /**
     * 1. Application.yml or .properties - parameters initialised
     * 2. MyRedisCacheConfigurationProperties -  parameters declared and later binding
     * 3. MyRedisCacheConfig -
     *  3.1.1 RedisStandaloneConfiguration - pass host and port params from 2.
     *     .2 LettuceConnectionFactory - create a connection using a connection factory object and pass in 3.1 to get the desired params
     *
     *  3.2 RedisTemplate - Create a RedisTemplate using 3.1.2 . This template serves as abstraction to carry out operations in redis
     *
     *  3.3 defaultRedisCacheConfiguration - Create a method which creates a bean of RedisCacheConfiguration.
     *                                      This bean will be used as the default RedisCacheConfiguration bean in redis.
     *                                      Its timeToLive is gotten from 2. above
     *  3.4 cacheManager - Create a cache manager to manage multiple RedisCacheConfiguration bean if set in 2.
     *                      else the default bean will be used
     * **/
}
