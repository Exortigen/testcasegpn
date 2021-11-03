package com.testcase.testcasegpn;

import com.testcase.testcasegpn.soapservice.SoapCallMethod;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.xml.soap.*;
import java.io.IOException;

@SpringBootApplication
public class TestcasegpnApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestcasegpnApplication.class, args);
        SoapCallMethod.callWeb("Add", 10, 5);

    }



}