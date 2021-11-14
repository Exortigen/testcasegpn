package com.testcase.testcasegpn.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.testcase.testcasegpn.entity.Request;
import com.testcase.testcasegpn.repository.RequestRepository;
import com.testcase.testcasegpn.service.MessageService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.DataInput;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/calculator")
public class MessageController {
    @Autowired
    private RequestRepository requestRepository;

    @Tag(name = "REST-контроллер", description = "Контроллер для обрбаботки HTTP-запроса")
    @GetMapping(value = "/{func}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addFunc(@Parameter(description = "HTTP-запрос") @RequestBody Request request,
                                          @PathVariable @Parameter(description = "Вычислительная операция") String func
    ) {
        MessageService messageService = new MessageService();
        try {
            messageService.functionSwitch(request, func, requestRepository);
            Map<String, Object> response = new HashMap<String, Object>();
            response.put(request.getMethod() + "Result", request.getResult());
            return new ResponseEntity<>(new ObjectMapper().writeValueAsString(response), HttpStatus.OK);
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Invalid request or arguments", HttpStatus.BAD_REQUEST);
        }
    }
    //a
}