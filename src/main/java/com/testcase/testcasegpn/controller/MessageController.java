package com.testcase.testcasegpn.controller;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.testcase.testcasegpn.soapservice.SoapCallMethod;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;




@RestController
@RequestMapping("/calculator")
public class MessageController {
    private final SoapCallMethod soapCallMethod = new SoapCallMethod();

    @GetMapping(value = "/{func}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JSONObject> addFunc(HttpEntity<String> arguments, @PathVariable String func){
        int intA = 0;
        int intB = 0;
        int result = 0;
        System.out.println(arguments.getBody());
        try(JsonParser ab = new JsonFactory().createParser(arguments.getBody());){
            while (ab.nextToken() != JsonToken.END_OBJECT){
                String fieldname = ab.getCurrentName();
                if("intA".equals(fieldname)){
                    ab.nextToken();
                    intA = ab.getValueAsInt();
                }
                if("intB".equals(fieldname)){
                    ab.nextToken();
                    intB = ab.getValueAsInt();
                }
            }
            switch (func){
                case "Add":
                    System.out.println("ad");
                    result = SoapCallMethod.callWeb(func, intA, intB);
                    break;
                case "Divide":
                    result = SoapCallMethod.callWeb(func, intA, intB);
                    break;
                case "Multiply":
                    result = SoapCallMethod.callWeb(func, intA, intB);
                    break;
                case "Subtract":
                    result = SoapCallMethod.callWeb(func, intA, intB);
                    break;

            }
            System.out.println(result);

            JSONObject response = new JSONObject();
            response.put("result",result);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (JsonGenerationException ex){
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}