package com.testcase.testcasegpn.controller;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.DataInput;
import java.io.IOException;

@RestController
public class MainController {
    @GetMapping(value = "api/plus", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JSONObject> plusFunc(HttpEntity<String> arguments){
        int a=0;
        int b=0;
        int result=0;
        System.out.println(arguments.getBody());
        try(JsonParser ab = new JsonFactory().createParser(arguments.getBody());){
        while (ab.nextToken() != JsonToken.END_OBJECT){
            String fieldname = ab.getCurrentName();
            if("a".equals(fieldname)){
                ab.nextToken();
                a = ab.getValueAsInt();
            }
            if("b".equals(fieldname)){
                ab.nextToken();
                b = ab.getValueAsInt();
            }
        }
            result=a+b;
        JSONObject response = new JSONObject();
        response.put("result",result);
            return new ResponseEntity<>(response,HttpStatus.OK);
        } catch (JsonGenerationException ex){
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }


    }

}
