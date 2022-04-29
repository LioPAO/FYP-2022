package com.finalyearproject.fyp;

import com.finalyearproject.fyp.config.redisconfig.MyRedisCacheConfigurationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
@EnableConfigurationProperties(MyRedisCacheConfigurationProperties.class)
public class FypApplication {

    public static void main(String[] args) {
        SpringApplication.run(FypApplication.class, args);
    }

}
