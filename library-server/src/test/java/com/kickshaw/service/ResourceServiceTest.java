package com.kickshaw.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ResourceServiceTest {
    @Autowired
    private ResourceService resourceService;

    @Test
    void testList() {
        System.out.println(resourceService.list());
    }
}