package com.kickshaw.service;

import com.kickshaw.entity.User;
import com.kickshaw.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {
    @Autowired
    private UserMapper userMapper;

    @Test
    void testListUsers() {
        User user = new User();
        System.out.println(userMapper.listUsers(user));
    }
}