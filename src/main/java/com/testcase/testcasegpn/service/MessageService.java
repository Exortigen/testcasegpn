package com.testcase.testcasegpn.service;

import com.testcase.testcasegpn.entity.Request;
import com.testcase.testcasegpn.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MessageService {
    @Autowired
    private RequestRepository requestRepository;

    public void functionSwitch(Request request, String func){
        SoapCallMethod callMethod = new SoapCallMethod();
        if (request.getIntA() != 0 && request.getIntB() != 0) {
            switch (func) {
                case "add":
                    request.setMethodInt(0);
                    request.setMethod("Add");
                    request.hashCode();
                    Optional<Request> requestOptional = requestRepository.findByPersonalhash(request.getPersonalhash());
                    if (requestOptional.isPresent()) {
                        request = requestOptional.get();
                    } else {
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
        }
}
