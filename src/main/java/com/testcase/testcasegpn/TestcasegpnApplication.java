package com.testcase.testcasegpn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class TestcasegpnApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestcasegpnApplication.class, args);
    }



}