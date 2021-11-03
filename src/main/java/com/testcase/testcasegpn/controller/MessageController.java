package com.testcase.testcasegpn.controller;

import com.fasterxml.jackson.core.*;
import com.testcase.testcasegpn.soapservice.SoapCallMethod;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

@RestController
@RequestMapping("/calculator")
public class MessageController {
    private final SoapCallMethod soapCallMethod = new SoapCallMethod();

    @GetMapping(value = "/{func}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addFunc(HttpEntity<String> arguments, @PathVariable String func){
        Integer intA = 0;
        Integer intB = 0;
        Integer result = 0;
        try(JsonParser ab = new JsonFactory().createParser(arguments.getBody())){
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
            if (intA != 0 && intB != 0) {
                switch (func) {
                    case "add":
                        result = SoapCallMethod.callWeb("Add", intA, intB);
                        break;
                    case "divide":
                        result = SoapCallMethod.callWeb("Divide", intA, intB);
                        break;
                    case "multiply":
                        result = SoapCallMethod.callWeb("Multiply", intA, intB);
                        break;
                    case "subtract":
                        result = SoapCallMethod.callWeb("Subtract", intA, intB);
                        break;
                }
                JsonFactory jsonFactory = new JsonFactory();
                OutputStream outputStream = new ByteArrayOutputStream();
                JsonGenerator jsonGenerator = jsonFactory.createGenerator(outputStream, JsonEncoding.UTF8);
                jsonGenerator.writeStartObject();
                jsonGenerator.writeNumberField("result", result);
                jsonGenerator.writeEndObject();
                jsonGenerator.close();
                return new ResponseEntity<>(outputStream.toString(), HttpStatus.OK);
            } else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}