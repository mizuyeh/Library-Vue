package com.kickshaw.redis;

import com.kickshaw.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @Description
 * @date 2021/3/14
 */
@SpringBootTest
public class RedisTest {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    void testSet() {
        redisTemplate.opsForValue().set("user1", new String("user1"));
        System.out.println(redisTemplate.opsForValue().get("user1"));
    }
}
