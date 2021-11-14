package com.testcase.testcasegpn.controller;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.testcase.testcasegpn.entity.Request;
import com.testcase.testcasegpn.repository.RequestRepository;
import com.testcase.testcasegpn.service.MessageService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/calculator")
public class MessageController {
    @Autowired
    private RequestRepository requestRepository;

    @GetMapping(value = "/{func}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addFunc(HttpEntity<String> arguments, @PathVariable String func){
        MessageService messageService = new MessageService();
        ObjectMapper mapper = new ObjectMapper();
        Request request;
        try {
            request = mapper.readValue(arguments.getBody(), Request.class);
            messageService.functionSwitch(request, func, requestRepository);
            Map<String,Object> response = new HashMap<String, Object>();
            response.put(request.getMethod()+"Result", request.getResult());
            return new ResponseEntity<>(mapper.writeValueAsString(response), HttpStatus.OK);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/fix", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> fixs() {
        Request request = new Request();
        try {
            List<Request> requestOptional = requestRepository.findAll();
            JsonFactory jsonFactory = new JsonFactory();
            OutputStream outputStream = new ByteArrayOutputStream();
            JsonGenerator jsonGenerator = jsonFactory.createGenerator(outputStream, JsonEncoding.UTF8);
            jsonGenerator.writeStartArray();
            for (Request request1 : requestOptional) {
                jsonGenerator.writeStartObject();
                jsonGenerator.writeNumberField("intA", request1.getIntA());
                jsonGenerator.writeNumberField("intB", request1.getIntB());
                jsonGenerator.writeNumberField("result", request1.getPersonalhash());
                jsonGenerator.writeEndObject();
            }
            jsonGenerator.writeEndArray();
            jsonGenerator.close();
            return new ResponseEntity<>(outputStream.toString(), HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}