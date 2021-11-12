package com.testcase.testcasegpn.controller;

import com.fasterxml.jackson.core.*;
import com.testcase.testcasegpn.soapservice.SoapCallMethod;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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

    @Tag(name = "REST-контроллер", description = "Контроллер для обрбаботки HTTP-запроса")
    @GetMapping(value = "/{func}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addFunc(@Parameter(description = "HTTP-запрос") HttpEntity<String> arguments,
                                          @PathVariable @Parameter(description = "Вычислительная операция") String func
    ){
        try(JsonParser ab = new JsonFactory().createParser(arguments.getBody())){
            Integer intA = 0;
            Integer intB = 0;
            Integer result = 0;
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
                    default: return new ResponseEntity<>("Invalid request or arguments",HttpStatus.BAD_REQUEST);
                }
                JsonFactory jsonFactory = new JsonFactory();
                OutputStream outputStream = new ByteArrayOutputStream();
                JsonGenerator jsonGenerator = jsonFactory.createGenerator(outputStream, JsonEncoding.UTF8);
                jsonGenerator.writeStartObject();
                jsonGenerator.writeNumberField("result", result);
                jsonGenerator.writeEndObject();
                jsonGenerator.close();
                return new ResponseEntity<>(outputStream.toString(), HttpStatus.OK);
            } else return new ResponseEntity<>("Invalid request or arguments",HttpStatus.BAD_REQUEST);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Invalid request or arguments", HttpStatus.BAD_REQUEST);
        }
    }

}