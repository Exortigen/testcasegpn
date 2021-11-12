package com.testcase.testcasegpn;

import com.testcase.testcasegpn.soapservice.SoapCallMethod;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import javax.xml.soap.*;
import java.io.IOException;

@SpringBootApplication
@EnableCaching
public class TestcasegpnApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestcasegpnApplication.class, args);
    }



}