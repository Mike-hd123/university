package com.mike;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

@SpringBootTest
class BoostrapApplicationTests {

    @Autowired
    private StringRedisTemplate redis;

    @Test
    void contextLoads() {
        redis.opsForValue().set("test1", "aaa", 1, TimeUnit.SECONDS);
        System.out.println(redis.opsForValue().get("test1"));
    }
    
    @Test
    void get() {
        System.out.println(redis.opsForValue().get("test1"));
    }

}
