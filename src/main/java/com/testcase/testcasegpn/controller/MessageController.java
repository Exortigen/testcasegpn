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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/calculator")
public class MessageController {
    @Autowired
    private RequestRepository requestRepository;

    @Tag(name = "REST-контроллер", description = "Контроллер для обрбаботки HTTP-запроса")
    @GetMapping(value = "/{func}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addFunc(@Parameter(description = "HTTP-запрос") HttpEntity<String> arguments,
                                          @PathVariable @Parameter(description = "Вычислительная операция") String func
    ) {
        MessageService messageService = new MessageService();
        ObjectMapper mapper = new ObjectMapper();
        Request request;
        try {
            request = mapper.readValue(arguments.getBody(), Request.class);
            messageService.functionSwitch(request, func, requestRepository);
            Map<String, Object> response = new HashMap<String, Object>();
            response.put(request.getMethod() + "Result", request.getResult());
            return new ResponseEntity<>(mapper.writeValueAsString(response), HttpStatus.OK);
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Invalid request or arguments", HttpStatus.BAD_REQUEST);
        }
    }
}