package com.testcase.testcasegpn.controller;

import com.fasterxml.jackson.core.*;
import com.testcase.testcasegpn.repository.RequestRepository;
import com.testcase.testcasegpn.entity.Request;
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
        Integer intA = 0;
        Integer intB = 0;
        Integer result = 0;
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
            if (request.getIntA() != 0 && request.getIntB() != 0) {
                switch (func) {
                    case "add":
                        request.setMethodInt(0);
                        request.setMethod("Add");
                        request.hashCode();
                        Optional<Request> requestOptional = requestRepository.findByPersonalhash(request.getPersonalhash());
                        if (requestOptional.isPresent()) {
                            System.out.println("Uje est");
                            request = requestOptional.get();
                        }
                        else{
                            request.setResult(callMethod.callWeb(request));
                        requestRepository.save(request);
                        }
                        break;
                    case "divide":
                        request.setMethodInt(1);
                        request.setMethod("Divide");
                        request.setResult(callMethod.callWeb(request));
                        break;
                    case "multiply":
                        request.setMethodInt(3);
                        request.setMethod("Multiply");
                        request.setResult(callMethod.callWeb(request));
                        break;
                    case "subtract":
                        request.setMethodInt(4);
                        request.setMethod("Subtract");
                        request.setResult(callMethod.callWeb(request));
                        break;
                }
                JsonFactory jsonFactory = new JsonFactory();
                OutputStream outputStream = new ByteArrayOutputStream();
                JsonGenerator jsonGenerator = jsonFactory.createGenerator(outputStream, JsonEncoding.UTF8);
                jsonGenerator.writeStartObject();
                jsonGenerator.writeNumberField("result", request.getResult());
                jsonGenerator.writeEndObject();
                jsonGenerator.close();
                return new ResponseEntity<>(outputStream.toString(), HttpStatus.OK);
            } else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}