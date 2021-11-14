package com.testcase.testcasegpn.controller;

import com.fasterxml.jackson.core.*;
import com.testcase.testcasegpn.repository.RequestRepository;
import com.testcase.testcasegpn.entity.Request;
import com.testcase.testcasegpn.service.MessageService;
import com.testcase.testcasegpn.service.SoapCallMethod;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.Optional;

@RestController
@RequestMapping("/calculator")
public class MessageController {
    @Autowired
    private RequestRepository requestRepository;


    @GetMapping(value = "/{func}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addFunc(HttpEntity<String> arguments, @PathVariable String func){
        SoapCallMethod callMethod = new SoapCallMethod();
        Request request = new Request();
        //Вот тут поаккуратее сделать как нибудь, убрать переменные
        Integer intA = 0;
        Integer intB = 0;
        Integer result = 0;
        //Через стрим нельзя сделать?
        try(JsonParser ab = new JsonFactory().createParser(arguments.getBody())){
            while (ab.nextToken() != JsonToken.END_OBJECT) {
                String fieldname = ab.getCurrentName();
                if ("intA".equals(fieldname)) {
                    ab.nextToken();
                    request.setIntA(ab.getValueAsInt());
                }
                if ("intB".equals(fieldname)) {
                    ab.nextToken();
                    request.setIntB(ab.getValueAsInt());
                }
            }

                JsonFactory jsonFactory = new JsonFactory();
                OutputStream outputStream = new ByteArrayOutputStream();
                JsonGenerator jsonGenerator = jsonFactory.createGenerator(outputStream, JsonEncoding.UTF8);
                jsonGenerator.writeStartObject();
                jsonGenerator.writeNumberField("result", request.getResult());
                jsonGenerator.writeEndObject();
                jsonGenerator.close();
                return new ResponseEntity<>(outputStream.toString(), HttpStatus.OK);
            } catch (IOException ioException) {
            ioException.printStackTrace();
        } else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}