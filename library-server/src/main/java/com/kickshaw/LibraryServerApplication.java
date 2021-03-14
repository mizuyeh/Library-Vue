package com.kickshaw;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.kickshaw.mapper")
public class LibraryServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(LibraryServerApplication.class, args);
    }

}
